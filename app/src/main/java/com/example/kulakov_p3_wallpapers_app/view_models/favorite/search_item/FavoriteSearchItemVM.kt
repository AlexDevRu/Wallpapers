package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import com.example.data.database.PhotoRepository

class FavoriteSearchItemVM(
    repository: PhotoRepository,
    private val refresh: () -> Unit
): SearchItemVM(repository) {

    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        refresh()
        updateQuery()
    }
}