package com.example.domain.repository

import com.example.domain.data.PhotoItem
import com.example.domain.common.Result

interface IPhotoRepository {
    fun getPhotos(): Result<List<PhotoItem>?>
}