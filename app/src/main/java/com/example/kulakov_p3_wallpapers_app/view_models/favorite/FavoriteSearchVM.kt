package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.database.PhotoRepository
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.SearchItemMapper
import com.example.kulakov_p3_wallpapers_app.adapters.FavoriteSearchItemsAdapter
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteSearchVM @Inject constructor(
    private val repository: PhotoRepository
): BaseVM() {
    private var searchJob: Job? = null
    private var currentSearchResult: Flow<PagingData<SearchQueryEntity>>? = null

    @get:Bindable
    val adapter = FavoriteSearchItemsAdapter(repository)

    @Bindable
    var listPosition = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.listPosition)
        }

    private fun getQueriesFlow(): Flow<PagingData<SearchQueryEntity>> {
        val newResult = repository.getFavoriteQueries().cachedIn(viewModelScope)
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

        listPosition = 0
    }
}