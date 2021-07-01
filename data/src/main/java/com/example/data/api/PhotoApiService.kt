package com.example.data.api

import com.example.domain.data.api.PhotoItemApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET(".")
    fun getPhotos(@Query("results") results: Int, @Query("seed") seed: String): Call<PhotoItemApiResponse>?
}