package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoRepository
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.PhotoItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoritePhotoItemVM

class FavoriteImagesAdapter(
    private val repository: PhotoRepository,
    private val navigateByDirection: (NavigationEvent) -> Unit
): PagingDataAdapter<PhotoItem, FavoriteImagesAdapter.PhotoItemViewHolder>(PhotoItemDiff()) {

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        getItem(position)?.let { photoItem -> holder.bind(photoItem, "photoItem_${position}") }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val binding = DataBindingUtil.inflate<PhotoFavoriteItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_favorite_item,
            parent,
            false
        )

        return PhotoItemViewHolder(binding, repository)
    }

    inner class PhotoItemViewHolder(
        private val binding: PhotoFavoriteItemBinding,
        repository: PhotoRepository
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = FavoritePhotoItemVM(repository, { refresh() }, navigateByDirection)
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem, transitionName: String) {
            binding.apply {
                viewModel?.photoItem = photoItem
                photoImageView.transitionName = transitionName
                viewModel?.detailExtras = FragmentNavigatorExtras(
                    binding.photoImageView to "imageView"
                )
                executePendingBindings()
            }
        }
    }
}