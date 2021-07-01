package com.example.domain.data.api

data class PhotoItemApiResponse(
    val color: String,
    val created_at: String,
    val height: Int,
    val id: String,
    val urls: Urls,
    val user: User,
    val width: Int,
    val description: String
)