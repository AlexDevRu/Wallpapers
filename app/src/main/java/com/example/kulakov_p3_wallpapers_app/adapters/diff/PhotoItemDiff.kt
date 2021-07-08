package com.example.kulakov_p3_wallpapers_app.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.data.models.PhotoItem

class PhotoItemDiff : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
}