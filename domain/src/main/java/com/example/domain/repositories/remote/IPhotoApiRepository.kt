package com.example.domain.repositories.remote

import com.example.domain.common.Result
import com.example.domain.models.MetaInfoPhotoSearch
import com.example.domain.models.SearchItem

interface IPhotoApiRepository<TPhotoFlow> {
    suspend fun getPhotos(query: String?): TPhotoFlow
    fun getSearchItem(): Result<SearchItem>
}
