package ru.music.singlealbumapp.dto

data class Media(
    val id: Long,
    val file: String,
    var mediaState: MediaState = MediaState.PAUSE,
    var artist: String,
    var duration: Int,
    var position: Int,
    var isNegativePositionShow: Boolean = false
)

enum class MediaState {
    PLAY, PAUSE
}