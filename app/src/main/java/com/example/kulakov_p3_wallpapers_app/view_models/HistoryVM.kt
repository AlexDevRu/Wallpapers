package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.aliases.SearchQueryFlow
import com.example.domain.repositories.local.IPhotoRepository
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.use_cases.queries.GetQueriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    val repository: ISearchQueryRepository<SearchQueryFlow>
): BaseVM() {

    private val getQueriesUseCase = GetQueriesUseCase(repository)

    private var flowQueries: SearchQueryFlow? = null

    suspend fun getQueries(): SearchQueryFlow {
        if(flowQueries == null) {
            flowQueries = getQueriesUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowQueries!!
    }
}