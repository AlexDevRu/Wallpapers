package com.example.domain.repositories.local

import com.example.domain.models.SearchItem

interface ISearchQueryRepository<TSearchQueryFlow> {
    suspend fun getQueries(): TSearchQueryFlow
    suspend fun getFavoriteQueries(): TSearchQueryFlow
    suspend fun insertQuery(query: SearchItem)
    suspend fun updateQuery(query: SearchItem)
    suspend fun deleteQuery(query: SearchItem)
}
