package com.example.domain.repositories.local

import com.example.domain.models.PhotoItem
import com.example.domain.models.User

interface IPhotoRepository<TPhotoFlow> {
    suspend fun getFavoritePhotos(): TPhotoFlow
    suspend fun addToFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun deleteFromFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun getUserByPhoto(photoItem: PhotoItem): User?
}
