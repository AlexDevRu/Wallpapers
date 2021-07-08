package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import android.util.Log
import com.example.data.database.PhotoRepository
import com.example.kulakov_p3_wallpapers_app.utils.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.views.fragments.HistoryFragmentDirections

class HistorySearchItemVM(
    repository: PhotoRepository,
    private val navigateByDirection: (NavigationEvent) -> Unit
): SearchItemVM(repository) {

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }

    override fun goToSearchScreen() {
        Log.w("asd", "click ${searchQuery}")
        val event = NavigationEvent()
        event.direction = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment(searchQuery)
        navigateByDirection(event)
    }
}