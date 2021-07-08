package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import com.example.data.database.PhotoRepository
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.views.fragments.FavoriteFragmentDirections

class FavoriteSearchItemVM(
    repository: PhotoRepository,
    private val refresh: () -> Unit,
    private val navigateByDirection: (NavigationEvent) -> Unit
): SearchItemVM(repository) {

    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        refresh()
        updateQuery()
    }

    override fun goToSearchScreen() {
        val event = NavigationEvent()
        event.direction = FavoriteFragmentDirections.actionFavoriteFragmentToSearchFragment(searchQuery)
        navigateByDirection(event)
    }
}