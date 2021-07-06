package com.example.kulakov_p3_wallpapers_app.di

import android.app.Application
import androidx.room.Room
import com.example.data.database.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val DATABASE_NAME = "photo-database"

    @Provides
    @Singleton
    fun providesDatabase(app: Application) = Room.databaseBuilder(
            app,
            PhotoDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    fun providesPhotoDao(db: PhotoDatabase) = db.photoDao()
}