package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.aliases.UpdateQueryUseCase
import com.example.domain.models.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.SearchItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.SearchFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item.FavoriteSearchItemVM

class FavoriteSearchItemsAdapter(
    private val updateQueryUseCase: UpdateQueryUseCase
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

        return SearchItemViewHolder(binding)
    }

    interface Delegate {
        fun onItemClick()
    }

    inner class SearchItemViewHolder(
        private val binding: SearchFavoriteItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = FavoriteSearchItemVM(updateQueryUseCase) { refresh() }
            binding.viewModel = viewModel
        }

        fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().favoriteFragmentNavigator.showSearch(viewModel?.searchQuery)
                    }
                }
                executePendingBindings()
            }
        }
    }
}