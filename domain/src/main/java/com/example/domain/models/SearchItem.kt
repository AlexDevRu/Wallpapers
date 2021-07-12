package com.example.domain.models

import java.util.*

data class SearchItem(
    val id: UUID = UUID.randomUUID(),
    var query: String? = null,
    var resultsCount: Int = 0,
    var date: Date = Date(),
    var isFavorite: Boolean = false
)