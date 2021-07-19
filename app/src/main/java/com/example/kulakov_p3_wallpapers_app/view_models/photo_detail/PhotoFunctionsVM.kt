package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.aliases.AddToFavoritePhotoItemUseCase
import com.example.kulakov_p3_wallpapers_app.utils.Utils
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PhotoFunctionsVM @Inject constructor(
    private val addToFavoritePhotoItemUseCase: AddToFavoritePhotoItemUseCase
): PhotoItemVM() {

    private val _liveSetWallpapers = MutableLiveData<Bitmap>()
    val liveSetWallpapers: LiveData<Bitmap> = _liveSetWallpapers

    private val _liveSetLockScreen = MutableLiveData<Bitmap>()
    val liveSetLockScreen: LiveData<Bitmap> = _liveSetLockScreen

    private val _closeDialog = MutableLiveData(false)
    val closeDialog: LiveData<Boolean> = _closeDialog

    private var setDesktopJob: Job? = null

    var isNetworkAvailable = false

    private suspend fun getPhotoBitmap(): Bitmap? {
        if(photoItem?.localPhotoPath != null) {
            return Utils.getBitmapByLocalPath(photoItem!!.localPhotoPath!!)
        }

        if(isNetworkAvailable)
            return Utils.getBitmapByUrl(photoItem!!.regular!!)

        return null
    }

    fun setWallpapersDesktop() {
        if(photoItem != null) {
            setDesktopJob?.cancel()
            setDesktopJob = viewModelScope.launch(Dispatchers.IO) {
                val image = getPhotoBitmap()
                _liveSetWallpapers.postValue(image)
                _closeDialog.postValue(true)
            }
        }
    }

    fun setWallpapersLockScreen() {
        if(photoItem != null) {
            Log.w("asd", "setWallpapersLockScreen")
            setDesktopJob?.cancel()
            setDesktopJob = viewModelScope.launch(Dispatchers.IO) {
                val image = getPhotoBitmap()
                _liveSetLockScreen.postValue(image)
                _closeDialog.postValue(true)
            }
        }
    }

    suspend fun saveToFavorite() {
        Log.e("asd", "photo path ${photoItem!!.localPhotoPath}")
        Log.e("asd", "user path ${photoItem!!.user!!.localPhotoPath}")
        addToFavoritePhotoItemUseCase.invoke(photoItem!!)
    }
}