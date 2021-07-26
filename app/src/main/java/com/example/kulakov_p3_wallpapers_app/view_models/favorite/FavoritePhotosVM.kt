package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.PhotoItemFlow
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.DeleteFromFavoritePhotoItemUseCase
import com.example.domain.use_cases.photo.GetFavoritePhotosUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosVM @Inject constructor(
    private val getFavoritePhotosUseCase: GetFavoritePhotosUseCase,
    private val deleteFromFavoritePhotoItemUseCase: DeleteFromFavoritePhotoItemUseCase
): BaseVM() {

    private var flowFavoritePhotos: PhotoItemFlow? = null

    private var deleteJob: Job? = null

    suspend fun getFavoritePhotos(): PhotoItemFlow {
        if(flowFavoritePhotos == null) {
            flowFavoritePhotos = getFavoritePhotosUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowFavoritePhotos!!
    }

    fun deleteFromFavoritePhotoItem(photoItem: PhotoItem) {
        deleteJob?.cancel()
        deleteJob = viewModelScope.launch(Dispatchers.IO) {
            deleteFromFavoritePhotoItemUseCase.invoke(photoItem)
        }
    }
}