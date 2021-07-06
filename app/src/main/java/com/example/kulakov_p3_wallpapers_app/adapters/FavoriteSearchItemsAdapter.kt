package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoDao
import com.example.domain.data.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.SearchItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.SearchFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.FavoriteSearchItemVM

class FavoriteSearchItemsAdapter(
    private val photoDao: PhotoDao,
    private val navigateByDirection: (NavDirections) -> Unit
): PagingDataAdapter<SearchItem, FavoriteSearchItemsAdapter.SearchItemViewHolder>(SearchItemDiff()) {

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        getItem(position)?.let { userPostEntity -> holder.bind(userPostEntity) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = DataBindingUtil.inflate<SearchFavoriteItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.search_favorite_item,
            parent,
            false
        )

        return SearchItemViewHolder(binding, photoDao)
    }

    inner class SearchItemViewHolder(
        private val binding: SearchFavoriteItemBinding,
        photoDao: PhotoDao
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = FavoriteSearchItemVM(photoDao, { refresh() }, navigateByDirection)
            binding.viewModel = viewModel
        }

        fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                executePendingBindings()
            }
        }
    }
}