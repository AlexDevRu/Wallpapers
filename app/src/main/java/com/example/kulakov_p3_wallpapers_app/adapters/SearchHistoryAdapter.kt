package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoRepository
import com.example.domain.models.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.SearchItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.SearchItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item.HistorySearchItemVM

class SearchHistoryAdapter(
    private val repository: PhotoRepository
): PagingDataAdapter<SearchItem, SearchHistoryAdapter.SearchItemViewHolder>(SearchItemDiff()) {

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        getItem(position)?.let { searchItem -> holder.bind(searchItem) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val binding = DataBindingUtil.inflate<SearchItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.search_item,
            parent,
            false
        )

        return SearchItemViewHolder(binding, repository)
    }

    interface Delegate {
        fun onItemClick()
    }

    inner class SearchItemViewHolder(
        private val binding: SearchItemBinding,
        repository: PhotoRepository
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = HistorySearchItemVM(repository)
            binding.viewModel = viewModel
        }

        fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().historyFragmentNavigator.showSearch(viewModel?.searchQuery)
                    }
                }
                executePendingBindings()
            }
        }
    }
}