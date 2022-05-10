package ru.music.singlealbumapp.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.dto.Album
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val client: OkHttpClient
) : Repository {

    private val gson = Gson()
    private val typeToken = object : TypeToken<Album>() {}

    override suspend fun getAlbum(): Album {
        val request: Request = Request.Builder()
            .url("${BuildConfig.BASE_URL}/album.json")
            .build()

        return client.newCall(request)
            .execute()
            .let { response -> response.body?.string() ?: throw RuntimeException("body is null") }
            .let<String, Album> { json -> gson.fromJson(json, typeToken.type) }
    }
}