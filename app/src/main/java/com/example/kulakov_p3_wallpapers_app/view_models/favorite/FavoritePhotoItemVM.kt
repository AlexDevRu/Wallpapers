package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.FragmentNavigator
import com.example.data.database.PhotoRepository
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import com.example.kulakov_p3_wallpapers_app.views.fragments.FavoriteFragmentDirections
import com.example.kulakov_p3_wallpapers_app.views.fragments.SearchFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritePhotoItemVM(
    private val repository: PhotoRepository,
    private val refresh: () -> Unit,
    private val navigateByDirection: (NavigationEvent) -> Unit
): BaseVM() {
    private var searchJob: Job? = null

    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val photoUrl: String?
        get() = photoItem?.regular

    fun deleteFromFavorite() {
        refresh()
        deleteFromFavoritePhotoItem()
    }

    var detailExtras: FragmentNavigator.Extras? = null

    fun navigatePhotoDetail() {
        val event = NavigationEvent()
        event.direction = FavoriteFragmentDirections.actionFavoriteFragmentToPhotoDetailFragment(photoItem)
        event.extras = detailExtras
        navigateByDirection(event)
    }

    private fun deleteFromFavoritePhotoItem() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if(photoItem != null)
                repository.deleteFromFavoritePhotoItem(photoItem!!)
        }
    }
}