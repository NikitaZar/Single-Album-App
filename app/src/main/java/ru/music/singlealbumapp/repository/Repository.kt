package ru.music.singlealbumapp.repository

import ru.music.singlealbumapp.dto.*

interface Repository {
    suspend fun getAlbum(): Album
}