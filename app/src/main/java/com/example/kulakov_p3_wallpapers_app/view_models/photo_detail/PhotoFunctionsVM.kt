package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.lifecycle.MutableLiveData
import com.example.domain.use_cases.photo.AddToFavoritePhotoItemUseCase
import com.example.domain.use_cases.photo.CheckFavoritePhotoUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class PhotoFunctionsVM @Inject constructor(
    private val addToFavoritePhotoItemUseCase: AddToFavoritePhotoItemUseCase,
    private val checkFavoritePhotoUseCase: CheckFavoritePhotoUseCase
): PhotoItemVM() {

    val isLoadingDialogOpen = MutableLiveData(false)

    suspend fun saveToFavorite() {
        photoItem!!.addedToFavorite = Date()
        addToFavoritePhotoItemUseCase.invoke(photoItem!!)
    }

    suspend fun checkFavoritePhoto(): Boolean {
        return photoItem != null && checkFavoritePhotoUseCase.invoke(photoItem!!)
    }
}