package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.aliases.PhotoItemFlow
import com.example.domain.repositories.local.IPhotoRepository
import com.example.domain.use_cases.photo.GetFavoritePhotosUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosVM @Inject constructor(
    val repository: IPhotoRepository<PhotoItemFlow>
): BaseVM() {

    private val getFavoritePhotosUseCase = GetFavoritePhotosUseCase(repository)

    private var flowFavoritePhotos: PhotoItemFlow? = null

    suspend fun getFavoritePhotos(): PhotoItemFlow {
        if(flowFavoritePhotos == null) {
            flowFavoritePhotos = getFavoritePhotosUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowFavoritePhotos!!
    }
}