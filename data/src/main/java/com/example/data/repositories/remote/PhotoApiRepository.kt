package com.example.data.repositories.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.data.api.ApiConstants.NETWORK_PAGE_SIZE
import com.example.data.api.PhotoApiService
import com.example.data.api.sources.PhotosPageSource
import com.example.domain.aliases.PhotoItemFlow
import com.example.domain.repositories.remote.IPhotoApiRepository
import com.example.domain.use_cases.photo.CheckFavoritePhotoUseCase
import com.example.domain.use_cases.queries.InsertQueryUseCase
import javax.inject.Inject

class PhotoApiRepository @Inject constructor(
    private val service: PhotoApiService,
    private val insertQueryUseCase: InsertQueryUseCase
): IPhotoApiRepository {

    override suspend fun getPhotos(query: String?): PhotoItemFlow {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PhotosPageSource(service, query) { result -> insertQueryUseCase.invoke(result) }
            }
        ).flow
    }
}