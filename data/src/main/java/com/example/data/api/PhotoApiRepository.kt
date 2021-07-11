package com.example.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.domain.models.PhotoItem
import com.example.data.sources.PhotosPageSource
import com.example.domain.models.SearchItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.domain.common.Result
import java.lang.Exception

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

    suspend fun getMetaFromPhotosSearch(query: String): Result<SearchItem> {
        return try {
            val response = service.getPhotos(query = query,
                clientId = PhotosPageSource.ACCESS_KEY
            )
            Result.Success(SearchItem(query = query, resultsCount = response.total))
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}