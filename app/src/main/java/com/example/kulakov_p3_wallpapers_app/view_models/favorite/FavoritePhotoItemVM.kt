package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.photo.DeleteFromFavoritePhotoItemUseCase
import com.example.domain.utils.IFileProvider
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritePhotoItemVM(
    private val deleteFromFavoritePhotoItemUseCase: DeleteFromFavoritePhotoItemUseCase,
    fileProvider: IFileProvider,
    private val refresh: () -> Unit
): PhotoItemVM(fileProvider) {
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