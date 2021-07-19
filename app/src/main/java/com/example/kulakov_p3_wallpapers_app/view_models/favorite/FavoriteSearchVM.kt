package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.aliases.GetFavoriteQueriesUseCase
import com.example.data.aliases.SearchQueryFlow
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteSearchVM @Inject constructor(
    private val getFavoriteQueriesUseCase: GetFavoriteQueriesUseCase
): BaseVM() {

    private var flowFavoriteQueries: SearchQueryFlow? = null

    suspend fun getFavoriteQueries(): SearchQueryFlow {
        if(flowFavoriteQueries == null) {
            flowFavoriteQueries = getFavoriteQueriesUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowFavoriteQueries!!
    }
}