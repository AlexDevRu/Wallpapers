package com.example.domain.models

import java.util.*

data class PhotoItem (
    val id: String,
    var width: Int = 0,
    var height: Int = 0,
    var created: Date = Date(),
    var color: String? = null,
    var description: String? = null,
    var thumb: String? = null,
    var regular: String? = null,
    var full: String? = null,
    var localPhotoPath: String? = null,
    var user: User? = null
)