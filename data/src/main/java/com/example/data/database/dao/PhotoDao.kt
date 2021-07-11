package com.example.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.UserEntity

@Dao
interface PhotoDao {
    @Query("select * from photos")
    fun getPhotos(): PagingSource<Int, PhotoItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPhotoItem(photoItemEntity: PhotoItemEntity)

    @Delete
    suspend fun deletePhotoItem(photoItemEntity: PhotoItemEntity)


    @Query("select * from users where id=:id")
    suspend fun getUserById(id: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)


    @Transaction
    suspend fun insertPhoto(photoItemEntity: PhotoItemEntity, user: UserEntity) {
        insertUser(user)
        photoItemEntity.userId = user.id
        createPhotoItem(photoItemEntity)
    }

    @Transaction
    suspend fun deletePhoto(photoItemEntity: PhotoItemEntity, user: UserEntity) {
        deletePhotoItem(photoItemEntity)
        val count = getCountPhotosByUserId(user.id)
        if(count == 0)
            deleteUser(user)
    }

    @Query("select count(*) from photos where userId=:userId")
    suspend fun getCountPhotosByUserId(userId: String): Int
}