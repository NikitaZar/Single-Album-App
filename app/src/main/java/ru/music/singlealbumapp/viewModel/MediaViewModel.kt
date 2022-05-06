package ru.music.singlealbumapp.viewModel

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.music.singlealbumapp.dto.Album
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
            _album.postValue(repository.getAlbum())
        } catch (e: IOException) {
            //TODO
            Log.i("Album", "Error loading")
        }
    }
}