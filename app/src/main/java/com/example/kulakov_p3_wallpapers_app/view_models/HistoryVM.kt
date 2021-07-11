package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PhotoRepository
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.SearchItemMapper
import com.example.kulakov_p3_wallpapers_app.adapters.SearchHistoryAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    val repository: PhotoRepository
): BaseVM() {
    private var currentReadResult: Flow<PagingData<SearchQueryEntity>>? = null

    private val _adapter = SearchHistoryAdapter(repository)

    private var readJob: Job? = null

    init {
        getQueries()
    }

    private fun getQueriesFlow(): Flow<PagingData<SearchQueryEntity>> {
        val newResult = repository.getQueries().cachedIn(viewModelScope)
        currentReadResult = newResult
        return newResult
    }

    fun getQueries() {
        readJob?.cancel()
        readJob = viewModelScope.launch(Dispatchers.IO) {
            getQueriesFlow().collectLatest {
                _adapter.submitData(it.map { SearchItemMapper.toModel(it) })
            }
        }

        listPosition = 0
    }

    @Bindable
    var listPosition = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.listPosition)
        }

    @get:Bindable
    val adapter: RecyclerView.Adapter<*>
        get() = _adapter
}