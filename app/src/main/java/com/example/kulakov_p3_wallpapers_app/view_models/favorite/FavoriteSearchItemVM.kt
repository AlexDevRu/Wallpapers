package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.navigation.NavDirections
import com.example.data.database.PhotoDao
import com.example.kulakov_p3_wallpapers_app.views.fragments.FavoriteFragmentDirections

class FavoriteSearchItemVM(
    photoDao: PhotoDao,
    private val refresh: () -> Unit,
    private val navigateByDirection: (NavDirections) -> Unit
): SearchItemVM(photoDao) {
    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        refresh()
        updateQuery()
    }

    override fun goToSearchScreen() {
        val direction = FavoriteFragmentDirections.actionFavoriteFragmentToSearchFragment(searchQuery)
        navigateByDirection(direction)
    }
}