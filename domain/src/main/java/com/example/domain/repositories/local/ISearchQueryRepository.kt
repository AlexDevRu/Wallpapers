package com.example.domain.repositories.local

interface ISearchQueryRepository<TModel> {
    fun getQueries(): Any
    fun getFavoriteQueries(): Any
    suspend fun insertQuery(model: TModel)
    suspend fun updateQuery(model: TModel)
    suspend fun deleteQuery(model: TModel)
}