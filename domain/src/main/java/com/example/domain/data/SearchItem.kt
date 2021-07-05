package com.example.domain.data

import java.util.*

data class SearchItem (
    val id: UUID = UUID.randomUUID(),
    var query: String = "",
    var resultsCount: Int = 0,
    var date: Date = Date(),
    var isFavorite: Boolean = false
)