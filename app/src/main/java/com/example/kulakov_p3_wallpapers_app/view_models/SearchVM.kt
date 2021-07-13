package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.repositories.remote.IPhotoApiRepository
import com.example.domain.use_cases.photo.GetMetaInfoPhotoSearchUseCase
import com.example.domain.use_cases.photo.GetPhotosUseCase
import com.example.domain.use_cases.queries.InsertQueryUseCase
import com.example.data.aliases.PhotoItemFlow
import com.example.data.aliases.SearchQueryFlow
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
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
    searchQueryRepository: ISearchQueryRepository<SearchQueryFlow>,
    apiRepository: IPhotoApiRepository<PhotoItemFlow>
) : BaseVM() {

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null

    private var currentQueryValue: String? = null
    val searchQuery = BehaviorSubject.createDefault("")

    private val compositeDisposable = CompositeDisposable()

    val collectData = MutableLiveData<Boolean>()
    val scrollList = SingleLiveEvent<Int>()

    var initialSearch = true


    private val insertQueryUseCase = InsertQueryUseCase(searchQueryRepository)
    private val getPhotosUseCase = GetPhotosUseCase(apiRepository)
    private val getMetaInfoPhotoSearchUseCase = GetMetaInfoPhotoSearchUseCase(apiRepository)


    init {
        compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                if(!initialSearch) searchByKeyword()
            })

        searchByKeyword()
    }

    /*@Bindable
    var searchQuery: String? = null
        set(value) {
            field = value
            searchByKeyword()
            notifyPropertyChanged(BR.searchQuery)
        }*/

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
        if(currentQueryValue != searchQuery.value && searchQuery.value?.isNotEmpty() == true) {
            viewModelScope.launch(Dispatchers.IO) {
                val res = getMetaInfoPhotoSearchUseCase.invoke(searchQuery.value.orEmpty())
                Log.w("asd", "item from api ${res}")
                when(res) {
                    is Result.Success -> {
                        error = null
                        insertQueryUseCase.invoke(res.value)
                    }
                    is Result.Failure -> error = res.throwable.localizedMessage
                }
            }
        }

        collectData.postValue(true)

        initialSearch = false
    }


    suspend fun searchPhotos(): Flow<PagingData<PhotoItem>> {
        val lastResult = currentSearchResult
        if (searchQuery.value == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = searchQuery.value

        val newResult = getPhotosUseCase.invoke(searchQuery.value).cachedIn(viewModelScope)

        currentSearchResult = newResult

        scrollList.postValue(0)

        return newResult
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}