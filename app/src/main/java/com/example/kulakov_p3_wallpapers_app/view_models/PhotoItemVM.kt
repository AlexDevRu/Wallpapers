package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.Bindable
import androidx.navigation.fragment.FragmentNavigator
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.views.fragments.SearchFragmentDirections

class PhotoItemVM(private val navigateByDirection: (NavigationEvent) -> Unit): BaseVM() {
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val thumb: String?
        get() = photoItem?.thumb


    var detailExtras: FragmentNavigator.Extras? = null

    fun navigateToDetail() {
        val event = NavigationEvent()
        event.direction = SearchFragmentDirections.actionSearchFragmentToPhotoDetailFragment(photoItem)
        event.extras = detailExtras
        navigateByDirection(event)
    }
}