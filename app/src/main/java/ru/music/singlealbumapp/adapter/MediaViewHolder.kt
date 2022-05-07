package ru.music.singlealbumapp.adapter

import android.annotation.SuppressLint
import android.media.MediaMetadataRetriever
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.music.singlealbumapp.BuildConfig
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.databinding.CardMediaBinding
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState
import java.text.SimpleDateFormat

class MediaViewHolder(
    private val binding: CardMediaBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(media: Media) {
        val url = "${BuildConfig.BASE_URL}/${media.file}"



        binding.apply {
            artist.text = media.artist
            title.text = media.file
            duration.text = media.duration

            btPlay.setImageDrawable(
                binding.root.context.getDrawable(
                    when (media.mediaState) {
                        MediaState.PAUSE -> R.drawable.ic_baseline_play_arrow_24
                        MediaState.PLAY -> R.drawable.ic_baseline_pause_24
                    }
                )
            )
            btPlay.setOnClickListener {
                btPlay.setImageDrawable(
                    it.context.getDrawable(
                        when (media.mediaState) {
                            MediaState.PAUSE -> {
                                onInteractionListener.onPlay(media.id)
                                progress.isVisible = true
                                R.drawable.ic_baseline_pause_24
                            }
                            MediaState.PLAY -> {
                                onInteractionListener.onPause(media.id)
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