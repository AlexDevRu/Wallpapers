package com.example.domain.repositories.remote

import com.example.domain.aliases.PhotoItemFlow

interface IPhotoApiRepository {
    suspend fun getPhotos(query: String?, isShouldSaveQuery: Boolean): PhotoItemFlow
}
