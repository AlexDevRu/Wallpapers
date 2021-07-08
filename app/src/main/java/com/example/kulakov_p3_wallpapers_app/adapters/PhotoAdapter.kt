package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.PhotoItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoItemBinding
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.view_models.PhotoItemVM

class PhotoAdapter(private val navigateByDirection: (NavigationEvent) -> Unit)
    : PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoItemHolder>(PhotoItemDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemHolder {
        val binding = DataBindingUtil.inflate<PhotoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )

        return PhotoItemHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoItemHolder, position: Int) {
        holder.bind(getItem(position), "photoItem_${position}")
    }

    inner class PhotoItemHolder(private val binding: PhotoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = PhotoItemVM(navigateByDirection)
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem?, transitionName: String) {
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