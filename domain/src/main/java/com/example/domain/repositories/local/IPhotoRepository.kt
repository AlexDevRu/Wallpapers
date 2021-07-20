package com.example.domain.repositories.local

import androidx.paging.PagingData
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface IPhotoRepository {
    suspend fun getFavoritePhotos(): Flow<PagingData<PhotoItem>>
    suspend fun addToFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun deleteFromFavoritePhotoItem(photoItem: PhotoItem)
    suspend fun getUserByPhoto(photoItem: PhotoItem): Result<User>
}
