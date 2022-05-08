package ru.music.singlealbumapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.adapter.MediaAdapter
import ru.music.singlealbumapp.adapter.OnInteractionListener
import ru.music.singlealbumapp.databinding.ActivityMainBinding
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import ru.music.singlealbumapp.viewModel.MediaViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MediaViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MediaAdapter(object : OnInteractionListener {
            override fun onPlay(media: Media) {
                viewModel.play(media)
            }

            override fun onPause(media: Media) {
                viewModel.pause(media)
            }
        })

        binding.list.adapter = adapter

        binding.btPlayAlbum.setOnClickListener {
            viewModel.playAll()
        }

        viewModel.playing.observe(this) { playing ->
            playing?.let { media ->
                binding.btPlayAlbum.setImageDrawable(
                    applicationContext.getDrawable(
                        when (media.mediaState) {
                            MediaState.PAUSE -> R.drawable.ic_baseline_play_arrow_24
                            MediaState.PLAY -> R.drawable.ic_baseline_pause_24
                        }
                    )
                )
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.album.observe(this) { album ->
            binding.apply {
                albumTitle.text = album.title
                artistName.text = album.artist
                published.text = album.published
                genre.text = album.genre
                adapter.submitList(album.tracks)
            }
        }
    }
}