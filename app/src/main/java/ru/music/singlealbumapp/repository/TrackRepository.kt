package ru.music.singlealbumapp.repository

import ru.music.singlealbumapp.dto.*

interface TrackRepository {
    suspend fun getAlbum(): Album
    suspend fun getTrack(): Track
}