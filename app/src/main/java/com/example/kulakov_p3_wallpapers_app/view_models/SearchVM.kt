package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.PhotoApiRepository
import com.example.domain.data.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchVM: BaseVM() {
    private val apiRepository = PhotoApiRepository()

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null
    private var currentQueryValue: String? = null
    var searchQuery: String? = null
    var adapter = PhotoAdapter()
    private var searchJob: Job? = null


    val livePhotoSearch = MutableLiveData<Boolean>()

    @Bindable
    var loading = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.loading)
        }

    @Bindable
    var columnListCount = 3
        set(value) {
            field = value
            notifyPropertyChanged(BR.columnListCount)
        }

    @get:Bindable
    val managerIcon
        get() = if(liveColumnCount.value == 3) R.drawable.grid_three else R.drawable.grid_two

    val liveColumnCount = MutableLiveData(3)

    fun changeColumnCount() {
        if(liveColumnCount.value == 2)
            liveColumnCount.value = 3
        else
            liveColumnCount.value = 2
        notifyPropertyChanged(BR.managerIcon)
    }

    fun searchByKeyword() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchPhotos().collectLatest {
                adapter.submitData(it)
            }
        }

        livePhotoSearch.value = true
    }

    private fun searchPhotos(): Flow<PagingData<PhotoItem>> {
        val lastResult = currentSearchResult
        if (searchQuery == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = searchQuery
        Log.w("asd", searchQuery.toString())
        val newResult: Flow<PagingData<PhotoItem>> = apiRepository.getSearchResultStream(searchQuery)
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    @get:Bindable
    val queryTextListener: SearchView.OnQueryTextListener
        get() {
            return object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchByKeyword()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchQuery = query.orEmpty()
                    return true
                }
            }
        }
}