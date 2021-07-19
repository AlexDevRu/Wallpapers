package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "photos")
data class PhotoItemEntity(
    @PrimaryKey val id: String,
    var width: Int = 0,
    var height: Int = 0,
    var created: Date = Date(),
    var color: String? = null,
    var description: String? = null,
    var thumb: String? = null,
    var regular: String? = null,
    var full: String? = null,
    var localPhotoPath: String? = null,
    var userId: String
)