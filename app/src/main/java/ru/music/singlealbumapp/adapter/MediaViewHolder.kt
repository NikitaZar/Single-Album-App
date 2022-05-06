package ru.music.singlealbumapp.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.music.singlealbumapp.databinding.CardMediaBinding
import ru.music.singlealbumapp.dto.Media

class MediaViewHolder(
    private val binding: CardMediaBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(media: Media) {
        binding.apply {
            trackName.text = media.id.toString()
            artistName.text = media.file

            btPlay.setOnClickListener {}
        }
    }
}