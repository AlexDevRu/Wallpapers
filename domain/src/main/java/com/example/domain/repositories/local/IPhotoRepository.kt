package com.example.domain.repositories.local

import com.example.domain.aliases.PhotoItemFlow
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.models.User

interface IPhotoRepository {
    suspend fun getFavoritePhotos(): PhotoItemFlow
    suspend fun addToFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun deleteFromFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun getUserByPhoto(photoItem: PhotoItem): Result<User>
    suspend fun isPhotoInFavorite(photoItem: PhotoItem): Boolean
}
