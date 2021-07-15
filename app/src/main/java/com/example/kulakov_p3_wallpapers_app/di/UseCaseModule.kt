package com.example.kulakov_p3_wallpapers_app.di

import com.example.data.aliases.PhotoItemFlow
import com.example.data.aliases.SearchQueryFlow
import com.example.domain.repositories.local.IPhotoRepository
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.repositories.remote.IPhotoApiRepository
import com.example.domain.use_cases.photo.*
import com.example.domain.use_cases.queries.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun providesAddToFavoritePhotoItemUseCase(repository: IPhotoRepository<PhotoItemFlow>)
            = AddToFavoritePhotoItemUseCase(repository)

    @Provides
    fun providesDeleteFromFavoritePhotoItemUseCase(repository: IPhotoRepository<PhotoItemFlow>)
            = DeleteFromFavoritePhotoItemUseCase(repository)

    @Provides
    fun providesGetFavoritePhotosUseCase(repository: IPhotoRepository<PhotoItemFlow>)
            = GetFavoritePhotosUseCase(repository)

    @Provides
    fun providesGetMetaInfoPhotoSearchUseCase(repository: IPhotoApiRepository<PhotoItemFlow>)
            = GetMetaInfoPhotoSearchUseCase(repository)

    @Provides
    fun providesGetPhotosUseCase(repository: IPhotoApiRepository<PhotoItemFlow>)
            = GetPhotosUseCase(repository)

    @Provides
    fun providesGetUserByPhotoUseCase(repository: IPhotoRepository<PhotoItemFlow>)
            = GetUserByPhotoUseCase(repository)

    @Provides
    fun providesDeleteQueryUseCase(repository: ISearchQueryRepository<SearchQueryFlow>)
            = DeleteQueryUseCase(repository)

    @Provides
    fun providesGetFavoriteQueriesUseCase(repository: ISearchQueryRepository<SearchQueryFlow>)
            = GetFavoriteQueriesUseCase(repository)

    @Provides
    fun providesGetQueriesUseCase(repository: ISearchQueryRepository<SearchQueryFlow>)
            = GetQueriesUseCase(repository)

    @Provides
    fun providesInsertQueryUseCase(repository: ISearchQueryRepository<SearchQueryFlow>)
            = InsertQueryUseCase(repository)

    @Provides
    fun providesUpdateQueryUseCase(repository: ISearchQueryRepository<SearchQueryFlow>)
            = UpdateQueryUseCase(repository)
}