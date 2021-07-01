package com.example.domain.data

import java.time.LocalDate

data class PhotoItem(
    var id: String =  "",
    var width: Int = 0,
    var height: Int = 0,
    var created: LocalDate = LocalDate.now(),
    var color: String = "",
    var photoUrl: String = ""
) {
}