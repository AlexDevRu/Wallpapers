package com.example.domain.models

data class MetaInfoPhotoSearch(
    val searchItem: SearchItem = SearchItem(),
    val errors: List<String>?,
)