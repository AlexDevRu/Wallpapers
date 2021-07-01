package com.example.domain.repository

import com.example.domain.data.PhotoItem

interface IPhotoRepository {
    fun getPhotos(): Result<List<PhotoItem>?>
    fun deletePhoto(item: PhotoItem)
}