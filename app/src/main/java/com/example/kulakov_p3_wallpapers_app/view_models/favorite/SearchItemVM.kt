package com.example.kulakov_p3_wallpapers_app.view_models.favorite

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.data.database.PhotoDao
import com.example.data.database.PhotoRepository
import com.example.domain.data.SearchItem
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class SearchItemVM(photoDao: PhotoDao): BaseVM() {
    private val repository = PhotoRepository(photoDao)

    var searchItem: SearchItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val favorite: Boolean
        get() = if(searchItem == null) false else searchItem!!.isFavorite

    @get:Bindable
    val searchQuery: String?
        get() = searchItem?.query

    @get:Bindable
    val searchDate: String?
        get() = if(searchItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(searchItem!!.date)
        else null

    @get:Bindable
    val resultsCount: Int?
        get() = searchItem?.resultsCount


    val liveDeleted = MutableLiveData<SearchItem>()

    private fun updateQuery() {
        viewModelScope.launch(Dispatchers.IO) {
            if(searchItem != null)
                repository.updateQuery(searchItem!!)
        }
        notifyPropertyChanged(BR.favorite)
    }

    fun deleteFromFavorite() {
        searchItem?.isFavorite = false
        liveDeleted.value = searchItem
        updateQuery()
    }

    fun updateFavoriteStatus() {
        if(searchItem != null) {
            searchItem!!.isFavorite = !searchItem!!.isFavorite
            updateQuery()
        }
    }
}