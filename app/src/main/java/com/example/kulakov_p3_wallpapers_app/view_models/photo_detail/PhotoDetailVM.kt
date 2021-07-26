package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.files.IFileProvider
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.GetUserByPhotoUseCase
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailVM @Inject constructor(
    private val getUserByPhotoUseCase: GetUserByPhotoUseCase,
    private val fileProvider: IFileProvider
): BaseVM() {
    val photoItemVM = ObservableField<PhotoItemVM>()

    @get:Bindable
    val photoItem
        get() = photoItemVM.get()?.photoItem!!

    val openLink = SingleLiveEvent<String>()
    private var getUserJob: Job? = null

    fun init(photoItem: PhotoItem?) {
        val photoObservable = PhotoItemVM(fileProvider = fileProvider)
        photoObservable.photoItem = photoItem
        photoItemVM.set(photoObservable)
        if(photoItem?.isFavorite == true)
            getUserFromDb()
    }

    private fun getUserFromDb() {
        getUserJob?.cancel()
        getUserJob = viewModelScope.launch(Dispatchers.IO) {
            when(val result = getUserByPhotoUseCase.invoke(photoItemVM.get()?.photoItem!!)) {
                is Result.Success -> {
                    Log.e("asd", "user ${result.value}")
                    photoItemVM.get()?.userVM?.user = result.value
                }
            }
        }
    }

    fun openTwitter() {
        openLink.value = "https://www.twitter.com/${photoItemVM.get()?.userVM?.user?.twitter_username}/"
    }

    fun openInstagram() {
        openLink.value = "https://www.instagram.com/${photoItemVM.get()?.userVM?.user?.instagram_username}/"
    }

    fun openPortfolio() {
        openLink.value = photoItemVM.get()?.userVM?.user?.portfolio_url
    }
}