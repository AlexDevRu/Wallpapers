package com.example.kulakov_p3_wallpapers_app.view_models.favorite.search_item

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import com.example.data.aliases.SearchQueryFlow
import com.example.domain.models.SearchItem
import com.example.domain.repositories.local.ISearchQueryRepository
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import com.example.kulakov_p3_wallpapers_app.view_models.BaseVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

abstract class SearchItemVM(repository: ISearchQueryRepository<SearchQueryFlow>): BaseVM() {
    private var searchJob: Job? = null
    private val updateQueryUseCase = UpdateQueryUseCase(repository)

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


    protected fun updateQuery() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            if(searchItem != null)
                updateQueryUseCase.invoke(searchItem!!)
        }
        notifyPropertyChanged(BR.favorite)
    }
}