package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.database.PhotoRepository
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritePhotosVM @Inject constructor(
    val repository: PhotoRepository
): BaseVM() {

    val flowFavoritePhotos = repository.getFavoritePhotos().cachedIn(viewModelScope)

}