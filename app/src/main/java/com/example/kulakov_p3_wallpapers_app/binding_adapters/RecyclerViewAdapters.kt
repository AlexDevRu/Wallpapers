package com.example.kulakov_p3_wallpapers_app.binding_adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("columnCount")
fun setColumnCount(recyclerView: RecyclerView, columnCount: Int) {
    if(recyclerView.layoutManager is GridLayoutManager)
        (recyclerView.layoutManager as GridLayoutManager).spanCount = columnCount
}

@BindingAdapter("scrollPosition")
fun setListPosition(recyclerView: RecyclerView, position: Int) {
    recyclerView.scrollToPosition(position)
}

@BindingAdapter("adapter")
fun setAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    recyclerView.adapter = adapter
}
