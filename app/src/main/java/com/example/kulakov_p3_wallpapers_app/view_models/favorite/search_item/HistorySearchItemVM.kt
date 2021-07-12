package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import com.example.data.database.PhotoRepository
import com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item.SearchItemVM

class HistorySearchItemVM(
    repository: PhotoRepository
): SearchItemVM(repository) {

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }
}