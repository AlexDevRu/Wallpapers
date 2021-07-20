package com.example.domain.repositories.local

import com.example.domain.aliases.SearchQueryFlow
import com.example.domain.models.SearchItem

interface ISearchQueryRepository {
    suspend fun getQueries(): SearchQueryFlow
    suspend fun getFavoriteQueries(): SearchQueryFlow
    suspend fun insertQuery(query: SearchItem)
    suspend fun updateQuery(query: SearchItem)
    suspend fun deleteQuery(query: SearchItem)
}
