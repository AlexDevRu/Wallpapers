package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.database.PhotoRepository
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject


@HiltViewModel
class PhotoFunctionsVM @Inject constructor(
    private val repository: PhotoRepository
): BaseVM() {

    val liveSetWallpapers = MutableLiveData<Bitmap>()
    val liveSetLockScreen = MutableLiveData<Bitmap>()
    val closeDialog = MutableLiveData(false)

    private var setDesktopJob: Job? = null
    private var saveFavoriteJob: Job? = null

    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    fun setWallpapersDesktop() {
        if(photoItem != null) {
            setDesktopJob?.cancel()
            setDesktopJob = viewModelScope.launch(Dispatchers.IO) {
                val url = URL(photoItem!!.regular)
                val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                liveSetWallpapers.postValue(image)
                closeDialog.postValue(true)
            }
        }
    }

    fun setWallpapersLockScreen() {
        if(photoItem != null) {
            Log.w("asd", "setWallpapersLockScreen")
            setDesktopJob?.cancel()
            setDesktopJob = viewModelScope.launch(Dispatchers.IO) {
                val url = URL(photoItem!!.regular)
                //val bytes = url.openStream()
                val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                liveSetLockScreen.postValue(image)
                closeDialog.postValue(true)
            }
        }
    }

    fun saveToFavorite() {
        if(photoItem != null) {
            saveFavoriteJob?.cancel()
            saveFavoriteJob = viewModelScope.launch(Dispatchers.IO) {
                repository.addToFavoritePhotoItem(photoItem!!)
                closeDialog.postValue(true)
            }
        }
    }
}