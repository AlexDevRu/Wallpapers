package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import com.example.data.aliases.SearchQueryFlow
import com.example.domain.repositories.local.ISearchQueryRepository

class FavoriteSearchItemVM(
    repository: ISearchQueryRepository<SearchQueryFlow>,
    private val refresh: () -> Unit
): SearchItemVM(repository) {

    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        refresh()
        updateQuery()
    }
}