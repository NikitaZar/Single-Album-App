package ru.music.singlealbumapp.viewModel

import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.dto.Album
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import ru.music.singlealbumapp.media.MediaLifecycleObserver
import ru.music.singlealbumapp.repository.Repository
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.function.IntToLongFunction
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
        val duration = metaData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?.toInt()
        SimpleDateFormat("mm:ss", Locale.US).also { media.duration = it.format(duration) }
    }

    private fun loadAlbum() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val album =
                repository.getAlbum().apply { tracks.map { it.mediaState = MediaState.PAUSE } }
            album.tracks.forEach { setMediaProperties(it) }
            _album.postValue(album)
        } catch (e: IOException) {
            //TODO
            Log.i("Album", "Error loading")
        }
    }

    private fun changeState(media: Media, newState: MediaState) {
        _album.value?.tracks?.find { it.id == media.id }?.also { it.mediaState = newState }
        val album = _album.value ?: throw RuntimeException("_album.value = null")
        _album.postValue(album)
    }

    fun play(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"
        mediaObserver.setDataSource(url)
        mediaObserver.apply {
            play()
            changeState(media, MediaState.PLAY)
        }
        lastPlayed.postValue(media)
    }

    fun pause(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"
        mediaObserver.setDataSource(url)
        mediaObserver.apply {
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

}
