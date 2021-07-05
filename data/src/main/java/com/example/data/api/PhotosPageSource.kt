package com.example.data.api

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.mappers.PhotoResponseMapper
import com.example.domain.data.PhotoItem
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 60

class PhotosPageSource(private val service: PhotoApiService,
                       private val query: String?): PagingSource<Int, PhotoItem>() {

    companion object {
        const val SECRET_KEY = "cGiA-zQITn8pXjm6LFRVxqL7xkirJqwBwCaHcMNB0pM"
        const val ACCESS_KEY = "DAfRSeCPQrk3mi4JVzVJ3NuTLSiPbHKHzCMfydsseDE"
    }

    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val photosResult = if(query.isNullOrEmpty()) {
                val response = service.getPhotos(position, params.loadSize, ACCESS_KEY)
                response
            } else {
                val response = service.getPhotos(position, query, params.loadSize, ACCESS_KEY)
                response.results
            }

            val photos = PhotoResponseMapper.toModel(photosResult)
            val nextKey = if (photos.isNullOrEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}