package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.aliases.GetPhotosUseCase
import com.example.domain.models.PhotoItem
import com.example.kulakov_p3_wallpapers_app.events.SingleLiveEvent
import com.example.kulakov_p3_wallpapers_app.view_models.base.BaseVM
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

    private val _collectData = MutableLiveData<Boolean>()
    val collectData: LiveData<Boolean> = _collectData

    val scrollList = SingleLiveEvent<Int>()

    private var initialSearch = true

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val columnListCount = ObservableInt(3)

    init {
        compositeDisposable.add(searchQuery
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                if(!initialSearch) searchByKeyword()
            })

        searchByKeyword()
    }

    fun changeColumnCount() {
        columnListCount.set(if(columnListCount.get() == 2) 3 else 2)
    }

    fun retrySearch() {
        currentQueryValue = null
        searchByKeyword()
    }

    private fun searchByKeyword() {
        _collectData.postValue(true)
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