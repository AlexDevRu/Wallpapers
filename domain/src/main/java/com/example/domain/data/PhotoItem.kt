package com.example.domain.data

import java.time.LocalDate
import java.util.*

data class PhotoItem(
    var id: String =  "",
    var width: Int = 0,
    var height: Int = 0,
    var created: Date = Date(),
    var color: String = "",
    var description: String = "",
    var thumb: String = "",
    var regular: String = "",
    var full: String = ""
) {
}