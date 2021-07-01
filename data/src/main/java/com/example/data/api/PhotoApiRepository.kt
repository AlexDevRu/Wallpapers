package com.example.data.api

import com.example.data.mappers.PhotoResponseMapper
import com.example.domain.data.PhotoItem
import com.example.domain.repository.IPhotoRepository
import com.example.domain.common.Result
import com.example.domain.data.api.PhotoResponseError
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class PhotoApiRepository: IPhotoRepository {
    companion object {
        const val BASE_URL = "https://api.unsplash.com/"
        const val SECRET_KEY = "cGiA-zQITn8pXjm6LFRVxqL7xkirJqwBwCaHcMNB0pM"
        const val ACCESS_KEY = "DAfRSeCPQrk3mi4JVzVJ3NuTLSiPbHKHzCMfydsseDE"
    }

    override fun getPhotos(): Result<List<PhotoItem>?> {
        return try {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(PhotoApiService::class.java)

            val resp = service.getPhotos(1, ACCESS_KEY)?.execute()
            if(resp!!.isSuccessful) {
                val result = resp.body()
                val list = PhotoResponseMapper.toModel(result)
                Result.Success(list)
            } else {
                val gson = GsonBuilder().create()
                val result = gson.fromJson(resp.errorBody()?.string(), PhotoResponseError::class.java)
                Result.Failure(Exception(result.errors))
            }
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}