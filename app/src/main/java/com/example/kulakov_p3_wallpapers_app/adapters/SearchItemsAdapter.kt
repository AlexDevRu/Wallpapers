package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoDao
import com.example.domain.data.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.SearchFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.databinding.SearchItemBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.SearchItemVM

class SearchItemsAdapter(private val photoDao: PhotoDao, private val lifecycleOwner: LifecycleOwner)
    : PagingDataAdapter<SearchItem, SearchItemsAdapter.ItemViewHolder>(SearchItemDiff()) {

    enum class VIEW_TYPES {
        FAVORITE, PLAIN
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        getItem(position)?.let { userPostEntity -> holder.bind(userPostEntity) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        if(viewType == 0) {
            val binding = DataBindingUtil.inflate<SearchFavoriteItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.search_favorite_item,
                parent,
                false
            )

            return FavoriteSearchItemViewHolder(binding, photoDao, lifecycleOwner)
        }

        val binding = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.search_item,
            parent,
            false
        )

        return PlainSearchItemViewHolder(binding, photoDao, lifecycleOwner)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if(item!= null && getItem(position)!!.isFavorite) return VIEW_TYPES.FAVORITE.ordinal
        return VIEW_TYPES.PLAIN.ordinal
    }

    abstract class ItemViewHolder(view: View, photoDao: PhotoDao) : RecyclerView.ViewHolder(view) {
        protected val viewModel = SearchItemVM(photoDao)
        abstract fun bind(searchItem: SearchItem)
    }

    inner class PlainSearchItemViewHolder(
        private val binding: SearchItemBinding,
        photoDao: PhotoDao,
        private val lifecycleOwner: LifecycleOwner
    ): ItemViewHolder(binding.root, photoDao) {
        init {
            binding.lifecycleOwner = lifecycleOwner
            binding.viewModel = viewModel
        }

        override fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                executePendingBindings()
            }
        }
    }

    inner class FavoriteSearchItemViewHolder(
        private val binding: SearchFavoriteItemBinding,
        photoDao: PhotoDao,
        lifecycleOwner: LifecycleOwner
    ): ItemViewHolder(binding.root, photoDao) {
        init {
            binding.lifecycleOwner = lifecycleOwner
            binding.viewModel = viewModel
        }

        override fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                viewModel.liveDeleted.observe(binding.lifecycleOwner!!) {
                    /*val newList = currentList.toMutableList()
                    newList.remove(staffItem)
                    submitList(newList)*/
                }
                executePendingBindings()
            }
        }
    }

    class SearchItemDiff : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem == newItem
    }
}