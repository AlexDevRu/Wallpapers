package com.example.domain.repositories.remote

import androidx.paging.PagingData
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface IPhotoApiRepository {
    suspend fun getPhotos(query: String?): Flow<PagingData<PhotoItem>>
    fun getSearchResult(): Result<SearchItem>
}
