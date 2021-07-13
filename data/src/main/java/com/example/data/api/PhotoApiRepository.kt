package com.example.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.aliases.PhotoItemFlow
import com.example.data.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.data.api.sources.PhotosPageSource
import com.example.domain.common.Result
import com.example.domain.models.SearchItem
import com.example.domain.repositories.remote.IPhotoApiRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PhotoApiRepository: IPhotoApiRepository<PhotoItemFlow> {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(PhotoApiService::class.java)

    override suspend fun getPhotos(query: String?): PhotoItemFlow {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotosPageSource(service, query)
            }
        ).flow
    }

    override suspend fun getMetaFromPhotosSearch(query: String): Result<SearchItem> {
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