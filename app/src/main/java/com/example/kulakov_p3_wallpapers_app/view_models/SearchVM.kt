package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.aliases.GetPhotosUseCase
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : BaseVM() {

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null

    private var currentQueryValue: String? = null
    val searchQuery = BehaviorSubject.createDefault("")

    private val compositeDisposable = CompositeDisposable()

    val collectData = MutableLiveData<Boolean>()
    val scrollList = SingleLiveEvent<Int>()

    var initialSearch = true

    init {
        compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                if(!initialSearch) searchByKeyword()
            })

        searchByKeyword()
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