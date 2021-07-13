package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.aliases.PhotoItemFlow
import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.IPhotoRepository
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.PhotoItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoritePhotoItemVM

class FavoriteImagesAdapter(
    private val repository: IPhotoRepository<PhotoItemFlow>
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

        return PhotoItemViewHolder(binding, repository)
    }

    interface Delegate {
        fun onItemClick()
    }

    inner class PhotoItemViewHolder(
        private val binding: PhotoFavoriteItemBinding,
        repository: IPhotoRepository<PhotoItemFlow>
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = FavoritePhotoItemVM(repository) { refresh() }
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem) {
            binding.apply {
                viewModel?.photoItem = photoItem
                photoImageView.transitionName = photoItem.id
                delegate = object: Delegate {
                    override fun onItemClick() {
                        val extras = FragmentNavigatorExtras(
                            binding.photoImageView to photoItem.id
                        )
                        Navigator.getInstance().favoriteFragmentNavigator.showPhotoDetail(photoItem, extras)
                    }
                }
                executePendingBindings()
            }
        }
    }
}