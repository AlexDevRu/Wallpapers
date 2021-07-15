package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.converters.PhotoTypeConverters
import com.example.data.database.dao.PhotoDao
import com.example.data.database.dao.SearchQueryDao
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.database.entities.UserEntity


@Database(entities = [
    PhotoItemEntity::class,
    SearchQueryEntity::class,
    UserEntity::class
], version = 1)
@TypeConverters(PhotoTypeConverters::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
    abstract fun searchQueryDao(): SearchQueryDao
}