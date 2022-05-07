package ru.music.singlealbumapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.adapter.MediaAdapter
import ru.music.singlealbumapp.adapter.OnInteractionListener
import ru.music.singlealbumapp.databinding.ActivityMainBinding
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import ru.music.singlealbumapp.media.MediaLifecycleObserver
import ru.music.singlealbumapp.viewModel.MediaViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var mediaObserver: MediaLifecycleObserver

    private val viewModel: MediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MediaAdapter(object : OnInteractionListener {
            override fun onPlay(id: Long) {
                viewModel.changeState(MediaState.PLAY, id)
            }

            override fun onPause(id: Long) {
                viewModel.changeState(MediaState.PAUSE, id)
            }
        })

        binding.list.adapter = adapter

        binding.btPlayAlbum.setOnClickListener {
            //TODO("Not yet implemented")
        }

        viewModel.album.observe(this) { album ->
            binding.apply {
                albumTitle.text = album.title
                artistName.text = album.artist
                published.text = album.published
                genre.text = album.genre
                adapter.submitList(album.tracks)

                album.tracks.forEach { media ->
                    val url = "${BuildConfig.BASE_URL}/${media.file}"
                    when (media.mediaState) {
                        MediaState.PAUSE -> {
                            mediaObserver.setDataSource(url)
                            mediaObserver.pause()
                            Log.i("Album", media.mediaState.toString())
                        }
                        else -> Unit
                    }
                }
                album.tracks.forEach { media ->
                    val url = "${BuildConfig.BASE_URL}/${media.file}"
                    when (media.mediaState) {
                        MediaState.PLAY -> {
                            mediaObserver.setDataSource(url)
                            mediaObserver.play()
                            Log.i("Album", media.mediaState.toString())
                        }
                        else -> Unit
                    }
                }
            }
        }

        lifecycle.addObserver(mediaObserver)
    }
}