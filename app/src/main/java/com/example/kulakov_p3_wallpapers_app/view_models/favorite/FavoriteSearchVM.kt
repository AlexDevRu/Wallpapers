package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.SearchQueryFlow
import com.example.domain.models.SearchItem
import com.example.domain.use_cases.queries.GetFavoriteQueriesUseCase
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSearchVM @Inject constructor(
    private val getFavoriteQueriesUseCase: GetFavoriteQueriesUseCase,
    private val updateQueryUseCase: UpdateQueryUseCase
): BaseVM() {

    private var updateJob: Job? = null

    private var flowFavoriteQueries: SearchQueryFlow? = null

    suspend fun getFavoriteQueries(): SearchQueryFlow {
        if(flowFavoriteQueries == null) {
            flowFavoriteQueries = getFavoriteQueriesUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowFavoriteQueries!!
    }

    fun deleteFromFavorite(searchItemVM: SearchItemVM?) {
        if(searchItemVM?.searchItem != null) {
            searchItemVM.favorite = false
            updateQuery(searchItemVM.searchItem!!)
        }
    }

    private fun updateQuery(searchItem: SearchItem) {
        updateJob?.cancel()
        updateJob = viewModelScope.launch(Dispatchers.IO) {
            updateQueryUseCase.invoke(searchItem)
        }
    }
}