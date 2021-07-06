package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoItemBinding
import com.example.kulakov_p3_wallpapers_app.view_models.PhotoItemVM

class PhotoAdapter(private val navigateByDirection: (NavDirections) -> Unit)
    : PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoItemHolder>(PhotoDiffUtilCallback()) {

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
        holder.bind(getItem(position))
    }

    inner class PhotoItemHolder(private val binding: PhotoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = PhotoItemVM(navigateByDirection)
            binding.viewModel = viewModel
        }

        fun bind(photoItem: PhotoItem?) {
            binding.apply {
                viewModel?.photoItem = photoItem
                executePendingBindings()
            }
        }
    }

    class PhotoDiffUtilCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem) = oldItem == newItem
    }
}