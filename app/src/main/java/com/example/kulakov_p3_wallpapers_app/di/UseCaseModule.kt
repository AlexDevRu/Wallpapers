package com.example.kulakov_p3_wallpapers_app.di

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
    fun providesAddToFavoritePhotoItemUseCase(repository: IPhotoRepository)
            = AddToFavoritePhotoItemUseCase(repository)

    @Provides
    fun providesDeleteFromFavoritePhotoItemUseCase(repository: IPhotoRepository)
            = DeleteFromFavoritePhotoItemUseCase(repository)

    @Provides
    fun providesGetFavoritePhotosUseCase(repository: IPhotoRepository)
            = GetFavoritePhotosUseCase(repository)

    @Provides
    fun providesGetPhotosUseCase(repository: IPhotoApiRepository)
            = GetPhotosUseCase(repository)

    @Provides
    fun providesGetUserByPhotoUseCase(repository: IPhotoRepository)
            = GetUserByPhotoUseCase(repository)

    @Provides
    fun providesCheckFavoritePhotoUseCase(repository: IPhotoRepository)
            = CheckFavoritePhotoUseCase(repository)


    @Provides
    fun providesDeleteQueryUseCase(repository: ISearchQueryRepository)
            = DeleteQueryUseCase(repository)

    @Provides
    fun providesGetFavoriteQueriesUseCase(repository: ISearchQueryRepository)
            = GetFavoriteQueriesUseCase(repository)

    @Provides
    fun providesGetQueriesUseCase(repository: ISearchQueryRepository)
            = GetQueriesUseCase(repository)

    @Provides
    fun providesInsertQueryUseCase(repository: ISearchQueryRepository)
            = InsertQueryUseCase(repository)

    @Provides
    fun providesUpdateQueryUseCase(repository: ISearchQueryRepository)
            = UpdateQueryUseCase(repository)
}