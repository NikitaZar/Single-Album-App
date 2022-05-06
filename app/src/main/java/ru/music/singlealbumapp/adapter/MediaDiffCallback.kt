package ru.music.singlealbumapp.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.music.singlealbumapp.dto.Media

class MediaDiffCallback : DiffUtil.ItemCallback<Media>() {
    override fun areItemsTheSame(oldItem: Media, newItem: Media) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Media, newItem: Media) = oldItem == newItem
}