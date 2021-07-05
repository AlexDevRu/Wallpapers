package com.example.data.api

import com.example.domain.data.api.PhotoItemApiResponse
import com.example.domain.data.api.PhotoSearchByKeywordResult
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("search/photos")
    suspend fun getPhotos(
        @Query("page") page: Int = 1,
        @Query("query") query: String?,
        @Query("per_page") perPage: Int = 1,
        @Query("client_id") clientId: String
    ): PhotoSearchByKeywordResult

    @GET("photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String
    ): List<PhotoItemApiResponse>
}
