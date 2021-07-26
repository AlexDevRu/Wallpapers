package com.example.kulakov_p3_wallpapers_app.view_models.base

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.viewModelScope
import com.example.domain.models.SearchItem
import com.example.domain.use_cases.queries.UpdateQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

open class SearchItemVM(searchItem: SearchItem? = null): BaseObservable() {

    @Bindable
    var searchItem = searchItem
        set(value) {
            field = value
            notifyChange()
        }

    @Bindable
    var favorite: Boolean = searchItem?.isFavorite == true
        get() = searchItem?.isFavorite == true
        set(value) {
            field = value
            if(searchItem != null) {
                searchItem?.isFavorite = value
                notifyPropertyChanged(BR.favorite)
            }
        }

    @get:Bindable
    val searchDate: String?
        get() = if(searchItem != null)
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(searchItem!!.date)
        else null
}