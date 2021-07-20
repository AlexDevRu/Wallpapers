package com.example.kulakov_p3_wallpapers_app.view_models.base

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import com.example.domain.models.SearchItem
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

open class SearchItemVM(private val updateQueryUseCase: UpdateQueryUseCase? = null): BaseVM() {

    private var searchJob: Job? = null

    @Bindable
    var searchItem: SearchItem? = null
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    val favorite: Boolean
        get() = if(searchItem == null) false else searchItem!!.isFavorite

    @get:Bindable
    val searchDate: String?
        get() = if(searchItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(searchItem!!.date)
        else null


    protected fun updateQuery() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if(searchItem != null)
                updateQueryUseCase?.invoke(searchItem!!)
        }
        notifyPropertyChanged(BR.favorite)
    }
}