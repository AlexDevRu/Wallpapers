package com.example.kulakov_p3_wallpapers_app.di

import android.app.Application
import androidx.room.Room
import com.example.data.repositories.remote.PhotoApiRepository
import com.example.data.api.PhotoApiService
import com.example.data.database.PhotoDatabase
import com.example.data.database.dao.PhotoDao
import com.example.data.database.dao.SearchQueryDao
import com.example.data.preferences.PersistantStorage
import com.example.data.repositories.local.PhotoRepository
import com.example.data.repositories.local.SearchQueryRepository
import com.example.domain.repositories.local.IPhotoRepository
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.repositories.remote.IPhotoApiRepository
import com.example.domain.use_cases.queries.InsertQueryUseCase
import com.example.domain.files.IFileProvider
import com.example.data.files.FileProvider
import com.example.domain.preferences.IPersistantStorage
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
    @Singleton
    fun providesPersistantStorage(app: Application): IPersistantStorage = PersistantStorage(app.applicationContext)

    @Provides
    fun providesPhotoDao(db: PhotoDatabase) = db.photoDao()

    @Provides
    fun providesSearchQueryDao(db: PhotoDatabase) = db.searchQueryDao()

    @Provides
    fun providesPhotoRepository(photoDao: PhotoDao, fileProvider: IFileProvider): IPhotoRepository
    = PhotoRepository(photoDao, fileProvider)

    @Provides
    fun providesSearchQueryRepository(searchQueryDao: SearchQueryDao): ISearchQueryRepository
    = SearchQueryRepository(searchQueryDao)

    @Provides
    fun providesApiRepository(insertQueryUseCase: InsertQueryUseCase, service: PhotoApiService): IPhotoApiRepository
    = PhotoApiRepository(service, insertQueryUseCase)

    @Provides
    fun providesFileProvider(app: Application): IFileProvider = FileProvider(app)
}