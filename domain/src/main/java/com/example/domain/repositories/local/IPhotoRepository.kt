package com.example.domain.repositories.local

import com.example.domain.models.User

interface IPhotoRepository<TModel> {
    fun getFavoritePhotos(): Any

    suspend fun addToFavoritePhotoItem(model: TModel)

    suspend fun deleteFromFavoritePhotoItem(model: TModel)

    suspend fun getUserByPhoto(model: TModel): User?
}