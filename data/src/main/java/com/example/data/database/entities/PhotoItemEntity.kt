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
    var color: String = "",
    var description: String = "",
    var thumb: String = "",
    var regular: String = "",
    var full: String = "",
    var isFavorite: Boolean = false
)