package ru.music.singlealbumapp.media

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData

class MediaLifecycleObserver {
    private var player: MediaPlayer? = MediaPlayer()
    val position = MutableLiveData<Int>(player?.currentPosition)

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