package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.databinding.Bindable
import com.example.data.database.PhotoDao
import javax.inject.Inject
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.data.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.views.fragments.PhotoDetailFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class PhotoDetailVM @Inject constructor(
    photoDao: PhotoDao
): BaseVM() {

    var photoItem: PhotoItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val fullImage: String?
        get() = photoItem?.regular

    @get:Bindable
    val toolbarTitle: String?
        get() = photoItem?.description

    val liveNavigateBack = MutableLiveData<NavDirections>()

    fun navigateBack() {
        Log.w("asd", "click")
        liveNavigateBack.value = PhotoDetailFragmentDirections.actionPhotoDetailFragmentToSearchFragment()
    }
}