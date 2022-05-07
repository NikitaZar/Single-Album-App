package ru.music.singlealbumapp.dto

data class Media(
    val id: Long,
    val file: String,
    var mediaState: MediaState = MediaState.PAUSE,
)

enum class MediaState {
    PLAY, PAUSE
}