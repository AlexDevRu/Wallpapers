package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.example.data.aliases.GetUserByPhotoUseCase
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PhotoInfoVM @Inject constructor(
    private val getUserByPhotoUseCase: GetUserByPhotoUseCase
): BaseVM() {
    val openLink = SingleLiveEvent<String>()

    private var userJob: Job? = null

    @Bindable
    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            if(field?.user != null) {
                getUserFromDb()
            }
            notifyChange()
        }

    private fun getUserFromDb() {
        userJob?.cancel()
        userJob = viewModelScope.launch(Dispatchers.IO) {
            when(val result = getUserByPhotoUseCase.invoke(photoItem!!)) {
                is Result.Success -> {
                    photoItem?.user = result.value
                    notifyChange()
                }
            }
        }
    }

    @get:Bindable
    val date: String?
        get() = if(photoItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(photoItem!!.created)
        else null

    @get:Bindable
    val hasInstagram: Boolean
        get() = !photoItem?.user?.instagram_username.isNullOrEmpty()

    @get:Bindable
    val hasTwitter: Boolean
        get() = !photoItem?.user?.twitter_username.isNullOrEmpty()

    @get:Bindable
    val hasPortfolio: Boolean
        get() = !photoItem?.user?.portfolio_url.isNullOrEmpty()

    @get:Bindable
    val hasDescription: Boolean
        get() = !photoItem?.user?.bio.isNullOrEmpty()


    val photoUrl: String?
        get() = if(photoItem?.localPhotoPath == null) photoItem?.regular else "file://${photoItem?.localPhotoPath}"

    val userPhotoUrl: String?
        get() = if(photoItem?.user?.localPhotoPath == null)
            photoItem?.user?.photoUrl
        else "file://${photoItem?.user?.localPhotoPath}"


    fun openTwitter() {
        openLink.value = "https://twitter.com/${photoItem?.user?.twitter_username}"
    }

    fun openInstagram() {
        openLink.value = "https://instagram.com/${photoItem?.user?.instagram_username}"
    }

    fun openPortfolio() {
        openLink.value = photoItem?.user?.portfolio_url
    }
}