package ru.music.singlealbumapp.dto

data class Media(
    val id: Long,
    val file: String,
    var mediaState: MediaState = MediaState.PAUSE,
    var artist: String,
    var duration: String,
    var position: Int
)

enum class MediaState {
    PLAY, PAUSE
}