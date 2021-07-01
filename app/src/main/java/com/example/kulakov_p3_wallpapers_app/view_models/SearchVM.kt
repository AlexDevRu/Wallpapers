package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.appcompat.widget.SearchView
import androidx.databinding.Bindable

class SearchVM: BaseVM() {


    fun searchByKeyword() {
        /*UnsplashPhotoPicker.init(
            this, // application
            "your access key",
            "your secret key"
        )*/
    }

    var searchQuery: String? = null

    @get:Bindable
    val queryTextListener: SearchView.OnQueryTextListener
        get() {
            return object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchByKeyword()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchQuery = query
                    return true
                }
            }
        }
}