package com.example.data.api.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.data.api.PhotoApiService
import com.example.data.api.sources.PhotosPageSource
import com.example.data.database.PhotoDatabase
import com.example.data.database.entities.RemoteKeys
import com.example.data.mappers.PhotoResponseMapper
import com.example.domain.models.PhotoItem
import retrofit2.HttpException
import java.io.IOException

/*@ExperimentalPagingApi
class UnsplashRemoteMediator(
    private val query: String?,
    private val service: PhotoApiService,
    private val photoDatabase: PhotoDatabase
) : RemoteMediator<Int, PhotoItem>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PhotoItem>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                photoDatabase.remoteKeysDao().remoteKeysPhotoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PhotoItem>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                photoDatabase.remoteKeysDao().remoteKeysPhotoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PhotoItem>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { photoId ->
                photoDatabase.remoteKeysDao().remoteKeysPhotoId(photoId)
            }
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PhotoItem>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val photosResult = if(query.isNullOrEmpty()) {
                service.getPhotos(page, state.config.pageSize, PhotosPageSource.ACCESS_KEY)
            } else {
                val response = service.getPhotos(page, query, state.config.pageSize,
                    PhotosPageSource.ACCESS_KEY
                )
                response.results
            }

            val photos = PhotoResponseMapper.toModel(photosResult)

            val endOfPaginationReached = photos.isEmpty()

            photoDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    photoDatabase.remoteKeysDao().clearRemoteKeys()
                    photoDatabase.photoDao().clearUnfavoritePhoto()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = photos.map {
                    RemoteKeys(photoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                photoDatabase.remoteKeysDao().insertAll(keys)
                photoDatabase.photoDao().insertPhotoList(photos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
}*/