package com.example.kulakov_p3_wallpapers_app.view_models

import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.preferences.PersistantStorage
import com.example.domain.models.PhotoItem
import com.example.domain.use_cases.photo.GetPhotosUseCase
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
    private val getPhotosUseCase: GetPhotosUseCase,
    private val storage: PersistantStorage
) : BaseVM() {

    private var currentSearchResult: Flow<PagingData<PhotoItem>>? = null

    private var currentQueryValue: String? = null
    val searchQuery = BehaviorSubject.createDefault(storage.getQuery().orEmpty())

    private val compositeDisposable = CompositeDisposable()

    private val _collectData = MutableLiveData<Boolean>()
    val collectData: LiveData<Boolean> = _collectData

    val scrollList = SingleLiveEvent<Int>()

    private var initialSearch = true

    val error = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val columnListCount = ObservableInt(storage.getColumnCount())
    val isNetworkAvailable = ObservableBoolean(false)

    init {
        compositeDisposable.add(searchQuery
            .filter { isNetworkAvailable.get() && !initialSearch }
            .debounce(1500, TimeUnit.MILLISECONDS)
            .subscribe { _ ->
                searchByKeyword()
            })

        /*isNetworkAvailable.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("asd", "inet")
                if(isNetworkAvailable.get() && initialSearch)
                    searchByKeyword()
            }
        })*/

        /*if(isNetworkAvailable.get())
            searchByKeyword()*/
        searchByKeyword()
    }

    fun changeColumnCount() {
        columnListCount.set(if(columnListCount.get() == 2) 3 else 2)
    }

    fun retrySearch() {
        currentSearchResult = null
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

    fun saveData() {
        storage.saveQuery(searchQuery.value)
        storage.saveColumnCount(columnListCount.get())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}