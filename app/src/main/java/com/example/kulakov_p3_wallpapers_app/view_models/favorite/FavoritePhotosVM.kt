package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.aliases.GetFavoritePhotosUseCase
import com.example.data.aliases.PhotoItemFlow
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosVM @Inject constructor(
    private val getFavoritePhotosUseCase: GetFavoritePhotosUseCase
): BaseVM() {

    private var flowFavoritePhotos: PhotoItemFlow? = null

    suspend fun getFavoritePhotos(): PhotoItemFlow {
        if(flowFavoritePhotos == null) {
            flowFavoritePhotos = getFavoritePhotosUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowFavoritePhotos!!
    }
}