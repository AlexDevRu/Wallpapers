package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.database.entities.UserEntity

@Dao
interface PhotoDao {
    @Query("select * from search_queries order by date desc")
    fun getQueries(): PagingSource<Int, SearchQueryEntity>

    @Query("select * from search_queries where isFavorite=1 order by date desc")
    fun getFavoriteQueries(): PagingSource<Int, SearchQueryEntity>

    @Insert
    suspend fun insertSearchQuery(query: SearchQueryEntity)

    @Update
    suspend fun updateSearchQuery(query: SearchQueryEntity)

    @Delete
    suspend fun deleteSearchQuery(query: SearchQueryEntity)


    @Query("select * from photos")
    fun getPhotos(): PagingSource<Int, PhotoItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavoritePhoto(photoItemEntity: PhotoItemEntity)

    @Delete
    suspend fun deleteFromFavoritePhoto(photoItemEntity: PhotoItemEntity)

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)


    @Transaction
    suspend fun insertPhoto(photoItemEntity: PhotoItemEntity, user: UserEntity) {
        insertUser(user)
        photoItemEntity.userId = user.id
        addToFavoritePhoto(photoItemEntity)
    }

    @Transaction
    suspend fun deletePhoto(photoItemEntity: PhotoItemEntity, user: UserEntity) {
        deleteUser(user)
        deleteFromFavoritePhoto(photoItemEntity)
    }

    @Query("select * from users where id=:id")
    suspend fun getUserById(id: String)
}