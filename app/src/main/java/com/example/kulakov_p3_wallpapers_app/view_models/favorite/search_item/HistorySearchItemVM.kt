package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import androidx.lifecycle.viewModelScope
import com.example.domain.use_cases.queries.DeleteQueryUseCase
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HistorySearchItemVM(
    updateQueryUseCase: UpdateQueryUseCase,
    private val deleteQueryUseCase: DeleteQueryUseCase
): SearchItemVM(updateQueryUseCase) {

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }

    private var deleteJob: Job? = null

    fun deleteFromDb() {
        if(searchItem != null) {
            deleteJob?.cancel()
            deleteJob = viewModelScope.launch(Dispatchers.IO) {
                deleteQueryUseCase.invoke(searchItem!!)
            }
        }
    }
}