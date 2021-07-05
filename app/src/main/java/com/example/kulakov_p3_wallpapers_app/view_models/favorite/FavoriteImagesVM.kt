package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import com.example.data.database.PhotoDao
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteImagesVM @Inject constructor(
    photoDao: PhotoDao
): BaseVM() {

}