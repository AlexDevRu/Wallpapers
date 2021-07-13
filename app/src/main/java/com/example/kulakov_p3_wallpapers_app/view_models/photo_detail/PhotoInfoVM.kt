package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import android.net.Uri
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.database.repositories.PhotoRepository
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.GetUserByPhotoUseCase
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
    repository: PhotoRepository
): BaseVM() {
    val liveIntent = SingleLiveEvent<String>()

    private var userJob: Job? = null

    private val getUserByPhotoUseCase = GetUserByPhotoUseCase(repository)

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
            val user = getUserByPhotoUseCase.invoke(photoItem!!)
            if (user != null) {
                photoItem?.user = user
                notifyChange()
            }
        }
    }

    @get:Bindable
    val date: String?
        get() = if(photoItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(photoItem!!.created)
        else null

    @get:Bindable
    val hasTwitter: Boolean
        get() = !photoItem?.user?.twitter_username.isNullOrEmpty()

    @get:Bindable
    val hasPortfolio: Boolean
        get() = !photoItem?.user?.portfolio_url.isNullOrEmpty()

    @get:Bindable
    val hasDescription: Boolean
        get() = !photoItem?.description.isNullOrEmpty()

    fun openTwitter() {
        openLink("https://twitter.com/${photoItem?.user?.twitter_username}")
    }

    fun openInstagram() {
        openLink("https://instagram.com/${photoItem?.user?.instagram_username}")
    }

    fun openPortfolio() {
        openLink(photoItem?.user?.portfolio_url)
    }

    private fun openLink(link: String?) {
        liveIntent.value = link
        /*val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        liveIntent.value = browserIntent*/
    }
}