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
import com.example.kulakov_p3_wallpapers_app.databinding.SearchFavoriteItemBinding
import com.example.kulakov_p3_wallpapers_app.navigators.Navigator
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM

class FavoriteSearchItemsAdapter(
    private val deleteFromFavoriteHandler: (searchItem: SearchItemVM?) -> Unit
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
        fun onDeleteFromFavorite()
    }

    inner class SearchItemViewHolder(
        private val binding: SearchFavoriteItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(searchItem: SearchItem) {
            val vm = ObservableField<SearchItemVM>()
            vm.set(SearchItemVM(searchItem))
            binding.apply {
                viewModel = vm
                delegate = object: Delegate {
                    override fun onItemClick() {
                        Navigator.getInstance().favoriteFragmentNavigator.showSearch(viewModel?.get()?.searchItem?.query)
                    }

                    override fun onDeleteFromFavorite() {
                        deleteFromFavoriteHandler(binding.viewModel?.get())
                        refresh()
                    }
                }
                executePendingBindings()
            }
        }
    }
}