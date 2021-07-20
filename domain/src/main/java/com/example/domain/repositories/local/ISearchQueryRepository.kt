package com.example.domain.repositories.local

import androidx.paging.PagingData
import com.example.domain.models.SearchItem
import kotlinx.coroutines.flow.Flow

interface ISearchQueryRepository {
    suspend fun getQueries(): Flow<PagingData<SearchItem>>
    suspend fun getFavoriteQueries(): Flow<PagingData<SearchItem>>
    suspend fun insertQuery(query: SearchItem)
    suspend fun updateQuery(query: SearchItem)
    suspend fun deleteQuery(query: SearchItem)
}
