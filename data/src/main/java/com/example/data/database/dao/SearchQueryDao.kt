package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.database.entities.SearchQueryEntity
import java.util.*

@Dao
interface SearchQueryDao {
    @Insert
    suspend fun create(query: SearchQueryEntity)

    @Query("select * from search_queries order by date desc")
    fun getQueries(): PagingSource<Int, SearchQueryEntity>

    @Query("select * from search_queries where id=:id")
    suspend fun getQueryById(id: UUID): SearchQueryEntity?

    @Query("select * from search_queries where isFavorite=1 order by date desc")
    fun getFavoriteQueries(): PagingSource<Int, SearchQueryEntity>

    @Update
    suspend fun update(query: SearchQueryEntity)

    @Delete
    suspend fun delete(query: SearchQueryEntity)
}