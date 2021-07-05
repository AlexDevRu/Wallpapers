package com.example.kulakov_p3_wallpapers_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.ItemErrorBinding
import com.example.kulakov_p3_wallpapers_app.databinding.ItemLoadingBinding

class PhotoLoadStateAdapter: LoadStateAdapter<PhotoLoadStateAdapter.ItemViewHolder>() {
    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(loadState: LoadState)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ItemViewHolder {
        return when(loadState) {
            LoadState.Loading -> {
                val binding = DataBindingUtil.inflate<ItemLoadingBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_loading,
                    parent,
                    false
                )
                ProgressViewHolder(binding)
            }
            is LoadState.Error -> {
                val binding = DataBindingUtil.inflate<ItemErrorBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_error,
                    parent,
                    false
                )
                ErrorViewHolder(binding)
            }
            is LoadState.NotLoading -> error("Not supported")
        }
    }

    private companion object {
        private const val ERROR = 1
        private const val PROGRESS = 0
    }

    class ProgressViewHolder internal constructor(
        private val binding: ItemLoadingBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            // Do nothing
        }

        companion object {

            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ProgressViewHolder {
                return ProgressViewHolder(
                    ItemLoadingBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }

    class ErrorViewHolder internal constructor(
        private val binding: ItemErrorBinding
    ) : ItemViewHolder(binding.root) {

        override fun bind(loadState: LoadState) {
            require(loadState is LoadState.Error)
            binding.errorMessage.text = loadState.error.localizedMessage
        }

        companion object {

            operator fun invoke(
                layoutInflater: LayoutInflater,
                parent: ViewGroup? = null,
                attachToRoot: Boolean = false
            ): ErrorViewHolder {
                return ErrorViewHolder(
                    ItemErrorBinding.inflate(
                        layoutInflater,
                        parent,
                        attachToRoot
                    )
                )
            }
        }
    }
}