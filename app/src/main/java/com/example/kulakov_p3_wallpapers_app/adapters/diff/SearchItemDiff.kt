package com.example.kulakov_p3_wallpapers_app.adapters.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.SearchItem

class SearchItemDiff : DiffUtil.ItemCallback<SearchItem>() {
    override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem == newItem
}