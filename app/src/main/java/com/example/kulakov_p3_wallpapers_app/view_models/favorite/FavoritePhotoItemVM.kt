package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import com.example.data.aliases.DeleteFromFavoritePhotoItemUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritePhotoItemVM(
    private val deleteFromFavoritePhotoItemUseCase: DeleteFromFavoritePhotoItemUseCase,
    private val refresh: () -> Unit
): PhotoItemVM() {
    private var searchJob: Job? = null

    fun deleteFromFavorite() {
        refresh()
        deleteFromFavoritePhotoItem()
    }

    private fun deleteFromFavoritePhotoItem() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if(photoItem != null)
                deleteFromFavoritePhotoItemUseCase.invoke(photoItem!!)
        }
    }
}