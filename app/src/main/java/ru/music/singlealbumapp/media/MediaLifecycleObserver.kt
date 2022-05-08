package ru.music.singlealbumapp.media

import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class MediaLifecycleObserver {
    private var player: MediaPlayer? = MediaPlayer()

    fun position() = player?.currentPosition ?: 0

    fun play() {
        player?.setOnPreparedListener {
            it.start()
        }
        player?.prepareAsync()
    }

    fun pause() {
        player?.setOnPreparedListener {
            it.pause()
        }
        player?.prepareAsync()
    }

    fun stop() {
        player?.setOnPreparedListener {
            it.stop()
        }
        player?.prepareAsync()
    }

    fun setDataSource(url: String) {
        player?.reset()
        player?.setDataSource(url)
    }

    fun clear() {
        player?.release()
        player = null
    }
}