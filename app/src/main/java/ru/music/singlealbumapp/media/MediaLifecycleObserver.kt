package ru.music.singlealbumapp.media

import android.media.MediaPlayer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class MediaLifecycleObserver : LifecycleEventObserver {
    private var player: MediaPlayer? = MediaPlayer()

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

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_PAUSE -> player?.pause()
            Lifecycle.Event.ON_STOP -> {
                player?.release()
                player = null
            }
            Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
            else -> Unit
        }
    }
}