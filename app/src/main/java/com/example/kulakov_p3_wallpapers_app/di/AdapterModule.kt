package com.example.kulakov_p3_wallpapers_app.di


import com.example.domain.use_cases.photo.DeleteFromFavoritePhotoItemUseCase
import com.example.domain.use_cases.queries.DeleteQueryUseCase
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.domain.files.IFileProvider
import com.example.kulakov_p3_wallpapers_app.adapters.FavoritePhotoAdapter
import com.example.kulakov_p3_wallpapers_app.adapters.FavoriteSearchItemsAdapter
import com.example.kulakov_p3_wallpapers_app.adapters.SearchHistoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {
    @Provides
    fun providesFavoritePhotoAdapter(deleteFromFavoritePhotoItemUseCase: DeleteFromFavoritePhotoItemUseCase, fileProvider: IFileProvider)
            = FavoritePhotoAdapter(deleteFromFavoritePhotoItemUseCase, fileProvider)

    @Provides
    fun providesFavoriteSearchItemsAdapter(updateQueryUseCase: UpdateQueryUseCase)
            = FavoriteSearchItemsAdapter(updateQueryUseCase)

    @Provides
    fun providesSearchHistoryAdapter(updateQueryUseCase: UpdateQueryUseCase, deleteQueryUseCase: DeleteQueryUseCase)
            = SearchHistoryAdapter(updateQueryUseCase, deleteQueryUseCase)
}