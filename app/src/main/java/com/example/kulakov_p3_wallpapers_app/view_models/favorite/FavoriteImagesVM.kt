package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.data.database.PhotoDao
import com.example.data.database.PhotoRepository
import com.example.data.database.entities.PhotoItemEntity
import com.example.data.database.entities.SearchQueryEntity
import com.example.data.mappers.PhotoItemMapper
import com.example.data.mappers.SearchItemMapper
import com.example.kulakov_p3_wallpapers_app.adapters.FavoriteImagesAdapter
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
class FavoriteImagesVM @Inject constructor(
    photoDao: PhotoDao
): BaseVM() {
    private val repository: PhotoRepository = PhotoRepository(photoDao)
    val adapter = FavoriteImagesAdapter(photoDao)

    private var getJob: Job? = null

    private var currentSearchResult: Flow<PagingData<PhotoItemEntity>>? = null

    val livePhotosLoading = MutableLiveData<Boolean>()

    private fun getQueriesFlow(): Flow<PagingData<PhotoItemEntity>> {
        val newResult = repository.getFavoritePhotos().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

    fun getFavoritePhotos() {
        getJob?.cancel()
        getJob = viewModelScope.launch(Dispatchers.IO) {
            getQueriesFlow().collectLatest {
                adapter.submitData(it.map { PhotoItemMapper.toModel(it) })
            }
        }

        livePhotosLoading.value = true
    }
}