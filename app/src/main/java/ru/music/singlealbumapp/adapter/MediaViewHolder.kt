package ru.music.singlealbumapp.adapter

import android.util.Log
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
            Log.i("Album", "${media.id}")
            artistName.text = media.file
            Log.i("Album", "${media.file}")
            btPlay.setOnClickListener {
                onInteractionListener.onPlay()
            }
        }
    }
}