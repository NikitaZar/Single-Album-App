package ru.music.singlealbumapp.adapter

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.databinding.CardMediaBinding
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import java.text.SimpleDateFormat
import java.util.*

class MediaViewHolder(
    private val binding: CardMediaBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(media: Media) {

        binding.apply {
            artist.text = media.artist
            title.text = media.file
            progress.progress = media.position
            progress.max = media.duration

            val formatter = SimpleDateFormat("mm:ss", Locale.US)

            btPlay.setImageDrawable(
                binding.root.context.getDrawable(
                    when (media.mediaState) {
                        MediaState.PAUSE -> {
                            duration.text = formatter.format(media.duration)
                            progress.isVisible = false
                            R.drawable.ic_baseline_play_arrow_24
                        }
                        MediaState.PLAY -> {
                            duration.text = formatter.format(media.position)
                            progress.isVisible = true
                            R.drawable.ic_baseline_pause_24
                        }
                    }
                )
            )
            btPlay.setOnClickListener {
                btPlay.setImageDrawable(
                    it.context.getDrawable(
                        when (media.mediaState) {
                            MediaState.PAUSE -> {
                                onInteractionListener.onPlay(media)
                                progress.isVisible = true
                                R.drawable.ic_baseline_pause_24
                            }
                            MediaState.PLAY -> {
                                onInteractionListener.onPause(media)
                                progress.isVisible = false
                                R.drawable.ic_baseline_play_arrow_24
                            }
                        }
                    )
                )
            }
        }
    }
}