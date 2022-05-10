package ru.music.singlealbumapp.viewModel

import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.dto.Album
import ru.music.singlealbumapp.dto.AlbumState
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import ru.music.singlealbumapp.media.MediaLifecycleObserver
import ru.music.singlealbumapp.repository.Repository
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val repository: Repository,
    private val mediaObserver: MediaLifecycleObserver
) : ViewModel() {
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    private val lastPlayed = MutableLiveData<Media>()
    val playing: LiveData<Media>
        get() = lastPlayed

    private var progressJob: Job? = null

    private val emptyAlbum = Album(
        AlbumState.LOADING,
        0,
        "",
        "",
        "",
        "",
        "",
        emptyList()
    )

    init {
        loadAlbum()
    }

    override fun onCleared() {
        super.onCleared()
        mediaObserver.clear()
    }

    private fun setMediaProperties(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"
        val metaData =
            MediaMetadataRetriever().apply { setDataSource(url) }
        media.artist = metaData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
            .toString()
        media.duration = metaData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.toInt() ?: 0
    }

    private fun loadAlbum() = viewModelScope.launch(Dispatchers.IO) {
        try {
            _album.postValue(emptyAlbum)
            val album =
                repository.getAlbum().apply {
                    tracks.map { it.mediaState = MediaState.PAUSE }
                }
            album.tracks.forEach { setMediaProperties(it) }
            _album.postValue(album.copy(state = AlbumState.LOADED))
        } catch (e: IOException) {
            _album.postValue(emptyAlbum.copy(state = AlbumState.ERROR))
        }
    }

    private fun changeState(media: Media, newState: MediaState) {
        _album.value?.tracks?.find { it.id == media.id }?.also { it.mediaState = newState }
        val album = _album.value ?: throw RuntimeException("_album.value = null")
        _album.postValue(album)
    }

    fun play(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"
        Log.i("Album", url)
        mediaObserver.apply {
            setDataSource(url)
            play()
            changeState(media, MediaState.PLAY)
        }
        lastPlayed.value?.mediaState = MediaState.PAUSE
        lastPlayed.postValue(media)

        progressJob?.cancel()
        progressJob = viewModelScope.launch {
            mediaObserver.getPosition()
                .collectLatest { position ->
                    media.position = position
                    lastPlayed.postValue(media)
                }
        }
        mediaObserver.onFinishListener {
            Log.i("Album", "done") //TODO issue
//            playNext(media)
        }
    }

    fun pause(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"
        mediaObserver.apply {
            setDataSource(url)
            pause()
            changeState(media, MediaState.PAUSE)
        }
        lastPlayed.postValue(media)
    }

    fun playAll() {
        lastPlayed.value?.let {
            when (it.mediaState) {
                MediaState.PAUSE -> {
                    play(it)
                }
                MediaState.PLAY -> {
                    pause(it)
                }
            }
        } ?: run {
            _album.value?.tracks?.first()?.let {
                play(it)
            }
        }
    }

    private fun playNext(media: Media) {
        val nextMedia = _album.value?.tracks?.first { it.id == media.id + 1 }
            ?: _album.value?.tracks?.first()
        media.mediaState = MediaState.PAUSE
        nextMedia?.let {
            Log.i("Album", "playNext =  $it")
            play(it)
        }
    }
}
