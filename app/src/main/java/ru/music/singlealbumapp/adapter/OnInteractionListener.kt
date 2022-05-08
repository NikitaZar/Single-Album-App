package ru.music.singlealbumapp.adapter

import ru.music.singlealbumapp.dto.Media

interface OnInteractionListener {
    fun onPlay(media: Media)
    fun onPause(media: Media)
}