package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.AddToFavoritePhotoItemUseCase
import com.example.domain.use_cases.photo.CheckFavoritePhotoUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject


@HiltViewModel
class PhotoFunctionsVM @Inject constructor(
    private val addToFavoritePhotoItemUseCase: AddToFavoritePhotoItemUseCase,
    private val checkFavoritePhotoUseCase: CheckFavoritePhotoUseCase
): BaseVM() {

    val photoItemVM = ObservableField<PhotoItemVM>()

    @get:Bindable
    val photoItem: PhotoItem
        get() = photoItemVM.get()?.photoItem!!

    val isLoadingDialogOpen = MutableLiveData(false)

    suspend fun saveToFavorite() {
        photoItemVM.get()?.photoItem!!.addedToFavorite = Date()
        addToFavoritePhotoItemUseCase.invoke(photoItemVM.get()?.photoItem!!)
    }

    suspend fun checkFavoritePhoto(): Boolean {
        return photoItemVM.get()?.photoItem != null && checkFavoritePhotoUseCase.invoke(photoItemVM.get()?.photoItem!!)
    }
}