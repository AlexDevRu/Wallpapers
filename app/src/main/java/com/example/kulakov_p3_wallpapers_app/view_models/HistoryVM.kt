package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.aliases.SearchQueryFlow
import com.example.domain.models.SearchItem
import com.example.domain.use_cases.queries.DeleteQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.domain.use_cases.queries.GetQueriesUseCase
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
import com.example.kulakov_p3_wallpapers_app.view_models.base.SearchItemVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

@HiltViewModel
class HistoryVM @Inject constructor(
    private val getQueriesUseCase: GetQueriesUseCase,
    private val updateQueryUseCase: UpdateQueryUseCase,
    private val deleteQueryUseCase: DeleteQueryUseCase
): BaseVM() {

    private var flowQueries: SearchQueryFlow? = null
    private var deleteJob: Job? = null
    private var updateJob: Job? = null

    suspend fun getQueries(): SearchQueryFlow {
        if(flowQueries == null) {
            flowQueries = getQueriesUseCase.invoke().cachedIn(viewModelScope)
        }
        return flowQueries!!
    }

    fun deleteFromDb(searchItemVM: SearchItemVM?) {
        if(searchItemVM?.searchItem != null) {
            deleteJob?.cancel()
            deleteJob = viewModelScope.launch(Dispatchers.IO) {
                deleteQueryUseCase.invoke(searchItemVM.searchItem!!)
            }
        }
    }

    fun updateSearchItemDate(searchItemVM: SearchItemVM?) {
        if(searchItemVM?.searchItem != null) {
            searchItemVM.searchItem?.date = Date()
            updateQuery(searchItemVM.searchItem!!)
        }
    }

    fun toggleFavoriteStatus(searchItemVM: SearchItemVM?) {
        if(searchItemVM?.searchItem != null) {
            searchItemVM.favorite = !searchItemVM.favorite
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