package com.example.data.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity

@Dao
interface PhotoDao {
    @Query("select * from photos")
    suspend fun getPhotos(): List<PhotoItemEntity>

    @Query("SELECT * FROM search_queries")
    fun getQueries(): PagingSource<Int, SearchQueryEntity>

    @Query("SELECT * FROM search_queries where isFavorite=1")
    fun getFavoriteQueries(): PagingSource<Int, SearchQueryEntity>

    @Insert
    suspend fun insertSearchQuery(query: SearchQueryEntity)

    @Update
    suspend fun updateSearchQuery(query: SearchQueryEntity)

    @Delete
    suspend fun deleteSearchQuery(query: SearchQueryEntity)
}