package ru.music.singlealbumapp.media

import android.media.MediaPlayer
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

class MediaLifecycleObserver {
    private var player: MediaPlayer? = MediaPlayer()

    fun getPosition() = flow {
        val context = currentCoroutineContext()
        player?.setOnCompletionListener {
            context.cancel()
        }

        while (context.isActive) {
            val currentPosition = player?.currentPosition ?: 0
            emit(currentPosition)
            delay(timeMillis = 500)
        }
    }

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


