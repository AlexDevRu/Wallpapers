package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM

class FavoriteSearchItemVM(
    updateQueryUseCase: UpdateQueryUseCase,
    private val refresh: () -> Unit
): SearchItemVM(updateQueryUseCase) {

    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        refresh()
        updateQuery()
    }
}