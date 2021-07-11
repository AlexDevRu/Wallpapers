package com.example.domain.models.api

data class PhotoSearchByKeywordResult(
    val total: Int = 0,
    val results: List<PhotoItemApiResponse> = emptyList(),
    val errors: List<String>? = null
)
