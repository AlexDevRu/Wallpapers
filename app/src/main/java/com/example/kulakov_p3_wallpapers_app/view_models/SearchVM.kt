package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.api.PhotoApiRepository
import com.example.data.database.PhotoRepository
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    //val searchQuery = BehaviorSubject.createDefault("")

    private val compositeDisposable = CompositeDisposable()

    val collectData = MutableLiveData<Boolean>()
    val scrollList = MutableLiveData<Int>()

    var initialSearch = true

    init {

        /*compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                if(!initialSearch) searchByKeyword()
            })*/
    }

    @Bindable
    var searchQuery: String? = null
        set(value) {
            field = value
            searchByKeyword()
            notifyPropertyChanged(BR.searchQuery)
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

    fun changeColumnCount() {
        columnListCount = if(columnListCount == 2) 3 else 2
    }

    fun retrySearch() {
        currentQueryValue = null
        searchByKeyword()
    }

    private fun searchByKeyword() {
        if(currentQueryValue != searchQuery && searchQuery?.isNotEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                val res = apiRepository.getMetaFromPhotosSearch(searchQuery.orEmpty())
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

        collectData.postValue(true)
    }


    fun searchPhotos(): Flow<PagingData<PhotoItem>> {
        val lastResult = currentSearchResult
        if (searchQuery == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = searchQuery

        val newResult = apiRepository.getSearchResultStream(searchQuery).cachedIn(viewModelScope)

        currentSearchResult = newResult

        scrollList.postValue(0)

        return newResult
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}