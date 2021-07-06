package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import android.util.Log
import androidx.navigation.NavDirections
import com.example.data.database.PhotoDao
import com.example.kulakov_p3_wallpapers_app.views.fragments.HistoryFragmentDirections

class HistorySearchItemVM(
    photoDao: PhotoDao,
    private val navigateByDirection: (NavDirections) -> Unit
): SearchItemVM(photoDao) {

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }

    override fun goToSearchScreen() {
        Log.w("asd", "click ${searchQuery}")
        val direction = HistoryFragmentDirections.actionHistoryFragmentToSearchFragment(searchQuery)
        navigateByDirection(direction)
    }
}