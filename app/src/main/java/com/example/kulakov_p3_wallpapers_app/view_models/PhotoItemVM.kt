package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.Bindable
import androidx.navigation.NavDirections
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.views.fragments.SearchFragmentDirections

class PhotoItemVM(private val navigateByDirection: (NavDirections) -> Unit): BaseVM() {
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val thumb: String?
        get() = photoItem?.thumb

    fun navigateToDetail() {
        val direction = SearchFragmentDirections.actionSearchFragmentToPhotoDetailFragment(photoItem)
        navigateByDirection(direction)
    }
}