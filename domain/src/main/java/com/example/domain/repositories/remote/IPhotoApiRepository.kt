package com.example.domain.repositories.remote

import com.example.domain.common.Result
import com.example.domain.models.SearchItem

interface IPhotoApiRepository<TPhotoFlow> {
    suspend fun getPhotos(query: String?): TPhotoFlow
    suspend fun getMetaFromPhotosSearch(query: String): Result<SearchItem>
}
