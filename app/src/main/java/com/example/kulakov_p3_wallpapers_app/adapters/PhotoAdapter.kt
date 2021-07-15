package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.PhotoItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.PhotoItemVM

class PhotoAdapter: PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoItemHolder>(PhotoItemDiff()) {

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
        getItem(position)?.let { photoItem -> holder.bind(photoItem) }
    }

    interface Delegate {
        fun onItemClick()
    }

    inner class PhotoItemHolder(private val binding: PhotoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = PhotoItemVM()
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem) {
            binding.apply {
                viewModel?.photoItem = photoItem
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().searchFragmentNavigator.showPhotoDetail(photoItem)
                    }
                }
                executePendingBindings()
            }
        }
    }
}