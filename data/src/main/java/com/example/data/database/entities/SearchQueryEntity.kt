package com.example.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "search_queries")
data class SearchQueryEntity(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var query: String = "",
    var resultsCount: Int = 0,
    var date: Date = Date(),
    var isFavorite: Boolean = false
) {
}