package com.example.kulakov_p3_wallpapers_app.di

import com.example.data.api.PhotoApiRepository
import com.example.data.api.PhotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(PhotoApiRepository.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun providesService(retrofit: Retrofit) = retrofit.create(PhotoApiService::class.java)
}