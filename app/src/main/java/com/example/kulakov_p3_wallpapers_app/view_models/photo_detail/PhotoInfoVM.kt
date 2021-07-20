package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.use_cases.photo.GetUserByPhotoUseCase
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.base.PhotoItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoInfoVM @Inject constructor(
    private val getUserByPhotoUseCase: GetUserByPhotoUseCase
): PhotoItemVM() {
    val openLink = SingleLiveEvent<String>()

    private var userJob: Job? = null

    init {
        photoItemObservable.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("asd", "1 ${photoItem?.user}")
                if(photoItem?.user?.name == null) {
                    getUserFromDb()
                }
            }
        })
    }

    private fun getUserFromDb() {
        userJob?.cancel()
        userJob = viewModelScope.launch(Dispatchers.IO) {
            when(val result = getUserByPhotoUseCase.invoke(photoItem!!)) {
                is Result.Success -> {
                    Log.e("asd", "user ${result.value}")
                    photoItem?.user = result.value
                    notifyChange()
                }
            }
        }
    }

    fun openTwitter() {
        openLink.value = "https://www.twitter.com/${photoItem?.user?.twitter_username}/"
    }

    fun openInstagram() {
        openLink.value = "https://www.instagram.com/${photoItem?.user?.instagram_username}/"
    }

    fun openPortfolio() {
        openLink.value = photoItem?.user?.portfolio_url
    }
}