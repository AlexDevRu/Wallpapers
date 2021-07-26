package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.files.IFileProvider
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.PhotoItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM

class FavoritePhotoAdapter(
    private val deleteFromFavoriteHandler: (photoItem: PhotoItem) -> Unit,
    private val fileProvider: IFileProvider
): PagingDataAdapter<PhotoItem, FavoritePhotoAdapter.PhotoItemViewHolder>(PhotoItemDiff()) {

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

        return PhotoItemViewHolder(binding)
    }

    interface Delegate {
        fun onItemClick()
        fun onDeleteFromFavorite()
    }

    inner class PhotoItemViewHolder(
        private val binding: PhotoFavoriteItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(photoItem: PhotoItem) {
            val vm = ObservableField<PhotoItemVM>()
            vm.set(PhotoItemVM(photoItem, fileProvider))
            binding.apply {
                viewModel = vm
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().favoriteFragmentNavigator.showPhotoDetail(photoItem)
                    }

                    override fun onDeleteFromFavorite() {
                        deleteFromFavoriteHandler(binding.viewModel?.get()?.photoItem!!)
                        refresh()
                    }
                }
                executePendingBindings()
            }
        }
    }
}