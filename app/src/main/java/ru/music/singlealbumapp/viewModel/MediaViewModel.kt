package ru.music.singlealbumapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.music.singlealbumapp.dto.Album
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import ru.music.singlealbumapp.repository.Repository
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album>
        get() = _album

    init {
        loadAlbum()
    }

    private fun loadAlbum() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val album =
                repository.getAlbum().apply { tracks.map { it.mediaState = MediaState.PAUSE } }
            _album.postValue(album)
        } catch (e: IOException) {
            //TODO
            Log.i("Album", "Error loading")
        }
    }

    fun changeState(mediaState: MediaState, id: Long) {
        _album.value?.tracks?.find { it.id == id }?.also { it.mediaState = mediaState }
        val album = _album.value ?: throw RuntimeException("_album.value = null")
        _album.postValue(album)
    }
}