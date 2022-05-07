package ru.music.singlealbumapp.adapter

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import ru.music.singlealbumapp.R
import ru.music.singlealbumapp.databinding.CardMediaBinding
import ru.music.singlealbumapp.dto.Media
import ru.music.singlealbumapp.dto.MediaState

class MediaViewHolder(
    private val binding: CardMediaBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(media: Media) {
        binding.apply {
            trackName.text = media.id.toString()
            artistName.text = media.file
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
                                R.drawable.ic_baseline_pause_24
                            }
                            MediaState.PLAY -> {
                                onInteractionListener.onPause(media.id)
                                R.drawable.ic_baseline_play_arrow_24
                            }
                        }
                    )
                )
            }
        }
    }
}