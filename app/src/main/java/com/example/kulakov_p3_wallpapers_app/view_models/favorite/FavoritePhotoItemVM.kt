package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.example.data.database.PhotoRepository
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritePhotoItemVM(
    private val repository: PhotoRepository,
    private val refresh: () -> Unit
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

    private fun deleteFromFavoritePhotoItem() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if(photoItem != null)
                repository.deleteFromFavoritePhotoItem(photoItem!!)
        }
    }
}