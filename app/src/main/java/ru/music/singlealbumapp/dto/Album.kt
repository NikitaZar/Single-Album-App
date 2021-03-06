package ru.music.singlealbumapp.dto

data class Album(
    var state: AlbumState,
    val id: Long,
    val title: String,
    val subtitle: String,
    val artist: String,
    val published: String,
    val genre: String,
    val tracks: List<Media>,
)