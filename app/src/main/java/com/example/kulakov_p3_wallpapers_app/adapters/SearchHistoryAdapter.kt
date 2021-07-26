package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.SearchItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.diff.SearchItemDiff
import com.example.kulakov_p3_wallpapers_app.databinding.SearchItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM

class SearchHistoryAdapter(
    private val toggleFavoriteStatusHandler: (searchItem: SearchItemVM?) -> Unit,
    private val updateDateHandler: (searchItem: SearchItemVM?) -> Unit,
    private val deleteHandler: (searchItem: SearchItemVM?) -> Unit
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
        fun onUpdateFavoriteStatus()
        fun onDelete()
    }

    inner class SearchItemViewHolder(
        private val binding: SearchItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(searchItem: SearchItem) {
            val vm = ObservableField<SearchItemVM>()
            vm.set(SearchItemVM(searchItem))
            binding.apply {
                viewModel = vm
                delegate = object: Delegate {
                    override fun onItemClick() {
                        updateDateHandler(viewModel?.get())
                        Navigator.getInstance().historyFragmentNavigator.showSearch(viewModel?.get()?.searchItem?.query)
                    }

                    override fun onUpdateFavoriteStatus() {
                        toggleFavoriteStatusHandler(viewModel?.get())
                    }

                    override fun onDelete() {
                        deleteHandler(viewModel?.get())
                    }
                }
                executePendingBindings()
            }
        }

        fun deleteItemFromDb() {
            binding.delegate?.onDelete()
        }
    }
}