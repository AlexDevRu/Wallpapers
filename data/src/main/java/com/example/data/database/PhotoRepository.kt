package com.example.data.database

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.SearchItemMapper
import com.example.domain.data.SearchItem
import kotlinx.coroutines.flow.Flow

class PhotoRepository(private val photoDao: PhotoDao) {
    companion object {
        const val QUERY_PAGE_SIZE = 20
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
}