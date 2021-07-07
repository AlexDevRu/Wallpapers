package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoDao
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoritePhotoItemVM

class FavoriteImagesAdapter(
    private val photoDao: PhotoDao
): PagingDataAdapter<PhotoItem, FavoriteImagesAdapter.PhotoItemViewHolder>(PhotoItemDiff()) {

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        getItem(position)?.let { photoItem -> holder.bind(photoItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding = DataBindingUtil.inflate<PhotoFavoriteItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_favorite_item,
            parent,
            false
        )

        return PhotoItemViewHolder(binding, photoDao)
    }

    inner class PhotoItemViewHolder(
        private val binding: PhotoFavoriteItemBinding,
        photoDao: PhotoDao
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = FavoritePhotoItemVM(photoDao) { refresh() }
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem) {
            binding.apply {
                viewModel?.photoItem = photoItem
                executePendingBindings()
            }
        }
    }

    class PhotoItemDiff: DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean = oldItem == newItem
    }
}