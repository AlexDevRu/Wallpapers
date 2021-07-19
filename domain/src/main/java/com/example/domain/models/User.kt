package com.example.domain.models

import java.util.*

data class User (
    val id: String = UUID.randomUUID().toString(),
    var bio: String? = null,
    var instagram_username: String? = null,
    var name: String? = null,
    var portfolio_url: String? = null,
    var twitter_username: String? = null,
    var username: String? = null,
    var photoUrl: String? = null,
    var localPhotoPath: String? = null,
)