package com.example.data.api.responses

data class PhotoSearchByKeywordResult(
    val total: Int = 0,
    val results: List<PhotoItemApiResponse> = emptyList(),
    val errors: List<String>? = null
)
