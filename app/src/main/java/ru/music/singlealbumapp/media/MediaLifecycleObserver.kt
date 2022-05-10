package ru.music.singlealbumapp.media

import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

class MediaLifecycleObserver {
    private var player: MediaPlayer? = MediaPlayer()

    fun onFinishListener(listener: () -> Unit) = player?.setOnCompletionListener {
        Log.i("Album", "onFinishListener position = ${it.currentPosition}")
        Log.i("Album", "onFinishListener duration = ${it.duration}")
        val duration = it.duration
        if (duration == it.currentPosition && duration != 0) {
            listener()
        }
    }

    fun getPosition() = flow {
        val context = currentCoroutineContext()

        while (context.isActive) {
            val currentPosition = player?.currentPosition ?: 0
            emit(currentPosition)
            delay(timeMillis = 500)
        }
        player?.setOnCompletionListener {
            context.cancel()
        }
    }

    fun play() {
        player?.setOnPreparedListener {
            it.start()
//            it.seekTo(it.duration - 15000) //TODO test
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


