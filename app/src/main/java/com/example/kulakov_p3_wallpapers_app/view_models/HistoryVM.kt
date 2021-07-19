package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.aliases.SearchQueryFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.data.aliases.GetQueriesUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM

@HiltViewModel
class HistoryVM @Inject constructor(
    private val getQueriesUseCase: GetQueriesUseCase
): BaseVM() {

    private var flowQueries: SearchQueryFlow? = null

    suspend fun getQueries(): SearchQueryFlow {
        if(flowQueries == null) {
            flowQueries = getQueriesUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowQueries!!
    }
}