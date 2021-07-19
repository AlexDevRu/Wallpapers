package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.aliases.DeleteQueryUseCase
import com.example.data.aliases.UpdateQueryUseCase
import com.example.domain.models.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.SearchItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.SearchItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item.HistorySearchItemVM

class SearchHistoryAdapter(
    private val updateQueryUseCase: UpdateQueryUseCase,
    private val deleteQueryUseCase: DeleteQueryUseCase
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

        return SearchItemViewHolder(binding)
    }

    interface Delegate {
        fun onItemClick()
    }

    inner class SearchItemViewHolder(
        private val binding: SearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val viewModel = HistorySearchItemVM(updateQueryUseCase, deleteQueryUseCase)

        init {
            binding.viewModel = viewModel
        }

        fun bind(searchItem: SearchItem) {
            binding.apply {
                viewModel?.searchItem = searchItem
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().historyFragmentNavigator.showSearch(viewModel?.searchItem?.query)
                    }
                }
                executePendingBindings()
            }
        }

        fun deleteItemFromDb() {
            viewModel.deleteFromDb()
        }
    }
}