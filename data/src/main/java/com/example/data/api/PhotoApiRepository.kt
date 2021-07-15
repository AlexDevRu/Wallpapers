package com.example.data.api

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.aliases.InsertQueryUseCase
import com.example.data.aliases.PhotoItemFlow
import com.example.data.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.data.api.sources.PhotosPageSource
import com.example.domain.common.Result
import com.example.domain.models.MetaInfoPhotoSearch
import com.example.domain.models.SearchItem
import com.example.domain.repositories.remote.IPhotoApiRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class PhotoApiRepository @Inject constructor(
    private val insertQueryUseCase: InsertQueryUseCase
): IPhotoApiRepository<PhotoItemFlow> {

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
                PhotosPageSource(service, query, insertQueryUseCase)
            }
        ).flow
    }

    private var meta: MetaInfoPhotoSearch? = null

    override fun getSearchItem(): Result<SearchItem> {
        if(meta?.errors == null) {
            return Result.Success(meta!!.searchItem)
        }
        return Result.Failure(Exception(meta?.errors?.joinToString("\n")))
    }
}