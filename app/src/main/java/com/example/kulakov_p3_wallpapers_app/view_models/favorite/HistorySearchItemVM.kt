package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import android.util.Log
import com.example.data.database.PhotoRepository
import com.example.kulakov_p3_wallpapers_app.events.NavigationEvent
import com.example.kulakov_p3_wallpapers_app.views.fragments.HistoryFragmentDirections

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