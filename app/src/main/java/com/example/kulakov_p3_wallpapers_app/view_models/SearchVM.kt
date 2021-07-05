package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.PhotoApiRepository
import com.example.data.database.PhotoDao
import com.example.data.database.PhotoRepository
import com.example.domain.data.PhotoItem
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    photoDao: PhotoDao
) : BaseVM() {
    private val apiRepository = PhotoApiRepository()
    private val repository = PhotoRepository(photoDao)

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null

    private var currentQueryValue: String? = null
    var searchQuery: String? = null

    val adapter = PhotoAdapter()

    private var searchJob: Job? = null

    val livePhotoSearch = MutableLiveData<Boolean>()
    val livePhotoError = MutableLiveData<String?>()

    init {
        adapter.addLoadStateListener { state ->
            loading = state.refresh == LoadState.Loading
            if(state.refresh is LoadState.Error) {
                livePhotoError.value = (state.refresh as LoadState.Error).error.localizedMessage
            } else {
                livePhotoError.value = null
            }
        }
    }

    @Bindable
    var loading: Boolean = false
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
        get() = if(columnListCount == 3) R.drawable.grid_three else R.drawable.grid_two

    @get:Bindable
    val managerIconVisible
        get() = searchQuery.isNullOrEmpty()

    fun changeColumnCount() {
        columnListCount = if(columnListCount == 2) 3 else 2
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

    private var searchSaved = false

    private fun searchPhotos(): Flow<PagingData<PhotoItem>> {
        val lastResult = currentSearchResult
        if (searchQuery == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = searchQuery
        Log.w("asd", "query ${searchQuery.toString()}")

        val newResult = apiRepository.getSearchResultStream(searchQuery).cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }

    @get:Bindable
    val queryTextListener: SearchView.OnQueryTextListener
        get() {
            return object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchSaved = false
                    Log.w("asd", "submit")
                    if(!searchSaved && !searchQuery.isNullOrEmpty()) {
                        viewModelScope.launch(Dispatchers.IO) {
                            val searchItem = apiRepository.getMetaFromPhotosSearch(searchQuery.toString())
                            Log.w("asd", "item from api ${searchItem}")
                            repository.insertQuery(searchItem)
                        }
                        searchSaved = true
                    }
                    searchByKeyword()
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchQuery = query
                    notifyPropertyChanged(BR.managerIconVisible)
                    return true
                }
            }
        }
}