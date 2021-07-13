package com.example.data.aliases

import androidx.paging.PagingData
import com.example.domain.models.PhotoItem
import com.example.domain.models.SearchItem
import kotlinx.coroutines.flow.Flow

typealias PhotoItemFlow = Flow<PagingData<PhotoItem>>
typealias SearchQueryFlow = Flow<PagingData<SearchItem>>