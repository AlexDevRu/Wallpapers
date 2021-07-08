package com.example.domain.data.api

data class PhotoItemApiResponse(
    val id: String?,
    val color: String?,
    val created_at: String?,
    val height: Int?,
    val urls: Urls?,
    val user: User?,
    val width: Int?,
    val description: String?
) {
    data class Urls(
        val full: String?,
        val regular: String?,
        val thumb: String?
    )
    data class User(
        val bio: String?,
        val id: String?,
        val instagram_username: String?,
        val name: String?,
        val portfolio_url: String?,
        val twitter_username: String?,
        val username: String?,
        val profile_image: ProfileImage?
    ) {
        data class ProfileImage(var large:  String?)
    }
}