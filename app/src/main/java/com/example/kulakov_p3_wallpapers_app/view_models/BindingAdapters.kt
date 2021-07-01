package com.example.kulakov_p3_wallpapers_app.view_models




import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter


@BindingAdapter("query")
fun setQuery(searchView: SearchView, queryText: String?) {
    searchView.setQuery(queryText, false)
}

@BindingAdapter("queryTextListener")
fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener?) {
    searchView.setOnQueryTextListener(listener)
}