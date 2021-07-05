package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity


@Database(entities = [PhotoItemEntity::class, SearchQueryEntity::class], version = 1)
@TypeConverters(PhotoTypeConverters::class)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao

    /*companion object {
        private var instance: PhotoDatabase? = null
        private const val DATABASE_NAME = "photo-database"

        // Get a database instance
        @Synchronized
        fun getDatabaseInstance(context: Context): PhotoDatabase? {
            if (instance == null) {
                instance = create(context)
            }
            return instance
        }

        // Create the database
        private fun create(context: Context): PhotoDatabase {
            val builder = Room.databaseBuilder(
                context.applicationContext,
                PhotoDatabase::class.java,
                DATABASE_NAME
            )
            return builder.build()
        }
    }*/
}