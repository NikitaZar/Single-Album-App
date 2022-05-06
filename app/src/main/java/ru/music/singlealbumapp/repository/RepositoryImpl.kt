package ru.music.singlealbumapp.repository

import ru.music.singlealbumapp.dto.Album
import javax.inject.Singleton

@Singleton
class RepositoryImpl : Repository {

    override suspend fun getAlbum(): Album {
        TODO("Not yet implemented")
    }
}