package com.example.kulakov_p3_wallpapers_app.binding_adapters


import android.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import java.util.*

@BindingAdapter("query")
fun setQuery(searchView: SearchView, query: String?) {
    if (Objects.equals(searchView.query.toString(), query)) {
        return
    }
    searchView.setQuery(query, false)
}

@InverseBindingAdapter(attribute = "query", event = "queryAttrChanged")
fun getQuery(searchView: SearchView): String {
    return searchView.query.toString()
}

@BindingAdapter("onQueryTextSubmit")
fun setOnQueryTextSubmitListener(
    searchView: SearchView,
    onQueryTextSubmitListener: OnQueryTextSubmitListener?
) {
    setOnQueryTextListener(searchView, onQueryTextSubmitListener, null)
}

@BindingAdapter("queryAttrChanged")
fun setQueryAttrChanged(searchView: SearchView, queryAttrChanged: InverseBindingListener?) {
    setOnQueryTextListener(searchView, null, queryAttrChanged)
}

@BindingAdapter("onQueryTextSubmit", "queryAttrChanged")
fun setOnQueryTextListener(
    searchView: SearchView,
    onQueryTextSubmitListener: OnQueryTextSubmitListener?,
    queryAttrChanged: InverseBindingListener?
) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onQueryTextSubmitListener?.onQueryTextSubmit(query)
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {
            queryAttrChanged?.onChange()
            return false
        }
    })
}

interface OnQueryTextSubmitListener {
    fun onQueryTextSubmit(query: String?)
}