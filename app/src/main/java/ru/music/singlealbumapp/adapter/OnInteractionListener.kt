package ru.music.singlealbumapp.adapter

interface OnInteractionListener {
    fun onPlay(id: Long)
    fun onPause(id: Long)
}