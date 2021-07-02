package com.example.kulakov_p3_wallpapers_app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.data.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.PhotoItemBinding
import com.example.kulakov_p3_wallpapers_app.view_models.PhotoItemVM


class PhotoAdapter: PagingDataAdapter<PhotoItem, PhotoAdapter.PhotoItemHolder>(PhotoDiffUtilCallback()) {
    var COLUMN_COUNT = 3

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): PhotoItemHolder {
        val binding = DataBindingUtil.inflate<PhotoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.photo_item,
            parent,
            false
        )

        /*val size = calculateSizeOfView(parent.context)
        val margin = getPx(parent.context, 5) * COLUMN_COUNT
        val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size - margin, size))

        layoutParams.bottomMargin = 5
        layoutParams.topMargin = 5

        binding.root.layoutParams = layoutParams*/

        return PhotoItemHolder(binding)
    }

    fun getPx(context: Context, dp: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5f).toInt()
    }

    fun calculateSizeOfView(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels
        return (dpWidth / COLUMN_COUNT)
    }

    override fun onBindViewHolder(@NonNull holder: PhotoItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoItemHolder(private val binding: PhotoItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        init {
            val viewModel = PhotoItemVM()
            binding.viewModel = viewModel
            /*val staffDao = StaffDatabase.getDatabaseInstance(itemView.context)!!.staffDao()
            viewModel.dao = staffDao
            binding.viewModel = viewModel
            viewModel.liveIntent.observe(lifecycleOwner, { intent ->
                if(intent != null)
                    itemView.context.startActivity(intent)
            }
            )
            viewModel.liveDeleted.observe(lifecycleOwner) { staffItem ->
                val newList = currentList.toMutableList()
                newList.remove(staffItem)
                submitList(newList)
            }*/
        }

        fun bind(photoItem: PhotoItem?) {
            binding.apply {
                viewModel?.photoItem = photoItem
                executePendingBindings()
            }
        }
    }

    class PhotoDiffUtilCallback : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoItem,
            newItem: PhotoItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}