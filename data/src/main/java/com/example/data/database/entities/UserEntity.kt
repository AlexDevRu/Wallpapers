package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val id: String,
    var bio: String? = null,
    var instagram_username: String? = null,
    var name: String? = null,
    var portfolio_url: String? = null,
    var twitter_username: String? = null,
    var username: String? = null,
    var photoUrl: String? = null,
    var localPhotoPath: String? = null
)