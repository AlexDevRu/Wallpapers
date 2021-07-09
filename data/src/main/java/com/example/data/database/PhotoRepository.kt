package com.example.data.database

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.database.dao.PhotoDao
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.PhotoItemMapper
import com.example.data.mappers.SearchItemMapper
import com.example.data.mappers.UserMapper
import com.example.data.models.PhotoItem
import com.example.data.models.User
import com.example.domain.data.SearchItem
import kotlinx.coroutines.flow.Flow

class PhotoRepository(private val photoDao: PhotoDao) {
    companion object {
        const val QUERY_PAGE_SIZE = 20
        const val PHOTO_PAGE_SIZE = 10
    }

    fun getQueries(): Flow<PagingData<SearchQueryEntity>> {
        return Pager(PagingConfig(QUERY_PAGE_SIZE)) {
            photoDao.getQueries()
        }.flow
    }

    fun getFavoriteQueries(): Flow<PagingData<SearchQueryEntity>> {
        return Pager(PagingConfig(QUERY_PAGE_SIZE)) {
            photoDao.getFavoriteQueries()
        }.flow
    }

    suspend fun insertQuery(query: SearchItem) {
        return photoDao.insertSearchQuery(SearchItemMapper.fromModel(query))
    }

    suspend fun updateQuery(query: SearchItem) {
        return photoDao.updateSearchQuery(SearchItemMapper.fromModel(query))
    }

    suspend fun deleteQuery(query: SearchItem) {
        return photoDao.deleteSearchQuery(SearchItemMapper.fromModel(query))
    }




    fun getFavoritePhotos(): Flow<PagingData<PhotoItemEntity>> {
        return Pager(PagingConfig(PHOTO_PAGE_SIZE)) {
            photoDao.getPhotos()
        }.flow
    }

    suspend fun addToFavoritePhotoItem(photoItem: PhotoItem) {
        return photoDao.insertPhoto(PhotoItemMapper.fromModel(photoItem), UserMapper.fromModel(photoItem.user))
    }

    suspend fun deleteFromFavoritePhotoItem(photoItem: PhotoItem) {
        return photoDao.deletePhoto(PhotoItemMapper.fromModel(photoItem), UserMapper.fromModel(photoItem.user))
    }

    suspend fun getUserByPhoto(photoItem: PhotoItem): User? {
        return UserMapper.toModel(photoDao.getUserById(photoItem.user.id))
    }
}