package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import com.example.data.aliases.SearchQueryFlow
import com.example.domain.repositories.local.ISearchQueryRepository

class HistorySearchItemVM(
    repository: ISearchQueryRepository<SearchQueryFlow>
): SearchItemVM(repository) {

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }
}