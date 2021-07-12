package com.example.kulakov_p3_wallpapers_app.view_models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.database.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryVM @Inject constructor(
    val repository: PhotoRepository
): BaseVM() {

    private val collectData = MutableLiveData<Boolean>()
    val flowQueries = repository.getQueries().cachedIn(viewModelScope)

    init {
        collectData.value = true
    }
}