package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.PhotoApiRepository
import com.example.data.database.PhotoRepository
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoAdapter
import com.example.kulakov_p3_wallpapers_app.adapters.PhotoLoadStateAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val repository: PhotoRepository,
    private val apiRepository: PhotoApiRepository
) : BaseVM() {

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null

    private var currentQueryValue: String? = null

    private var searchJob: Job? = null

    private val compositeDisposable = CompositeDisposable()
    val searchQuery = BehaviorSubject.createDefault("")

    private val _adapter = PhotoAdapter()

    init {
        _adapter.addLoadStateListener { state ->
            loading = state.refresh == LoadState.Loading
            error =
                if(state.refresh is LoadState.Error)
                    (state.refresh as LoadState.Error).error.localizedMessage
                else null
        }

        compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                searchByKeyword()
            })
    }

    @Bindable
    var error: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.error)
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

    @Bindable
    var listPosition = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.listPosition)
        }

    @get:Bindable
    val adapter = _adapter.withLoadStateHeaderAndFooter(
        PhotoLoadStateAdapter(), PhotoLoadStateAdapter()
    )

    fun changeColumnCount() {
        columnListCount = if(columnListCount == 2) 3 else 2
    }

    fun retrySearch() {
        currentQueryValue = null
        searchByKeyword()
    }

    private fun searchByKeyword() {
        if(currentQueryValue != searchQuery.value && searchQuery.value?.isNotEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                val res = apiRepository.getMetaFromPhotosSearch(searchQuery.value.orEmpty())
                Log.w("asd", "item from api ${res}")
                when(res) {
                    is Result.Success -> {
                        error = null
                        repository.insertQuery(res.value)
                    }
                    is Result.Failure -> error = res.throwable.localizedMessage
                }
            }
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchPhotos().collectLatest {
                _adapter.submitData(it)
            }
        }

        listPosition = 0
    }

    private fun searchPhotos(): Flow<PagingData<PhotoItem>> {
        val lastResult = currentSearchResult
        if (searchQuery.value == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = searchQuery.value

        val newResult = apiRepository.getSearchResultStream(searchQuery.value).cachedIn(viewModelScope)

        currentSearchResult = newResult
        return newResult
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}