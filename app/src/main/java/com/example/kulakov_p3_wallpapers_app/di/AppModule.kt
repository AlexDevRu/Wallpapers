package com.example.kulakov_p3_wallpapers_app.di

import android.app.Application
import androidx.room.Room
import com.example.data.aliases.PhotoItemFlow
import com.example.data.aliases.SearchQueryFlow
import com.example.data.api.PhotoApiRepository
import com.example.data.database.dao.PhotoDao
import com.example.data.database.PhotoDatabase
import com.example.data.database.repositories.PhotoRepository
import com.example.data.database.dao.SearchQueryDao
import com.example.data.database.repositories.SearchQueryRepository
import com.example.domain.repositories.local.IPhotoRepository
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.repositories.remote.IPhotoApiRepository
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

    @Provides
    fun providesSearchQueryDao(db: PhotoDatabase) = db.searchQueryDao()

    @Provides
    fun providesPhotoRepository(photoDao: PhotoDao): IPhotoRepository<PhotoItemFlow>
    = PhotoRepository(photoDao)

    @Provides
    fun providesSearchQueryRepository(searchQueryDao: SearchQueryDao): ISearchQueryRepository<SearchQueryFlow>
    = SearchQueryRepository(searchQueryDao)

    @Provides
    fun providesApiRepository(): IPhotoApiRepository<PhotoItemFlow>
    = PhotoApiRepository()
}