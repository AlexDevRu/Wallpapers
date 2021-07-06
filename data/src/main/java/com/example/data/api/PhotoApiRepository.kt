package com.example.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.models.PhotoItem
import com.example.domain.data.SearchItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoApiRepository {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PhotoApiService::class.java)

    fun getSearchResultStream(query: String?): Flow<PagingData<PhotoItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotosPageSource(service, query) }
        ).flow
    }

    suspend fun getMetaFromPhotosSearch(query: String): SearchItem {
        val response = service.getPhotos(query = query,
            clientId = PhotosPageSource.ACCESS_KEY
        )
        return SearchItem(query = query, resultsCount = response.total)
    }
}