package com.example.data.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithPhoto (
    @Embedded val user: UserEntity,

    @Relation(parentColumn = "id", entityColumn = "user_id")
    var photos: List<PhotoItemEntity>? = null
)