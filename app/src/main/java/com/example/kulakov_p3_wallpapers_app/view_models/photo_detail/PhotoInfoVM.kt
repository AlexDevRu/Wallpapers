package com.example.kulakov_p3_wallpapers_app.view_models.photo_detail

import android.content.Intent
import android.net.Uri
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.database.PhotoRepository
import com.example.domain.models.PhotoItem
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
    private val repository: PhotoRepository
): BaseVM() {
    val liveIntent = MutableLiveData<Intent>()

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
            val user = repository.getUserByPhoto(photoItem!!)
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
        val browserIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(link)
        )
        liveIntent.value = browserIntent
    }
}