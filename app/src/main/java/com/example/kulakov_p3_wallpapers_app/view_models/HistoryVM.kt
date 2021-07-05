package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.database.PhotoDao
import com.example.data.database.PhotoRepository
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.SearchItemMapper
import com.example.kulakov_p3_wallpapers_app.adapters.SearchItemsAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    photoDao: PhotoDao
): BaseVM() {
    private val repository: PhotoRepository = PhotoRepository(photoDao)

    private var currentSearchResult: Flow<PagingData<SearchQueryEntity>>? = null

    val adapter = SearchItemsAdapter(photoDao)

    private var searchJob: Job? = null

    val liveQueriesLoading = MutableLiveData<Boolean>()

    private fun getQueriesFlow(): Flow<PagingData<SearchQueryEntity>> {
        val newResult = repository.getQueries().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getQueries() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            getQueriesFlow().collectLatest {
                adapter.submitData(it.map { SearchItemMapper.toModel(it) })
            }
        }

        liveQueriesLoading.value = true
    }
}