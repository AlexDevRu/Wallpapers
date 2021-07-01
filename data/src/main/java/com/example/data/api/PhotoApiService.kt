package com.example.data.api

import com.example.domain.data.api.PhotoItemApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("photos")
    fun getPhotos(@Query("page") page: Int, @Query("client_id") clientId: String): Call<List<PhotoItemApiResponse>>?
}