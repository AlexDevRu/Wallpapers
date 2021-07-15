package com.example.data.aliases

import androidx.paging.PagingData
import com.example.domain.models.PhotoItem
import com.example.domain.models.SearchItem
import kotlinx.coroutines.flow.Flow
import com.example.domain.use_cases.photo.AddToFavoritePhotoItemUseCase
import com.example.domain.use_cases.photo.DeleteFromFavoritePhotoItemUseCase
import com.example.domain.use_cases.photo.GetFavoritePhotosUseCase
import com.example.domain.use_cases.photo.GetMetaInfoPhotoSearchUseCase
import com.example.domain.use_cases.photo.GetPhotosUseCase
import com.example.domain.use_cases.photo.GetUserByPhotoUseCase
import com.example.domain.use_cases.queries.DeleteQueryUseCase
import com.example.domain.use_cases.queries.GetFavoriteQueriesUseCase
import com.example.domain.use_cases.queries.GetQueriesUseCase
import com.example.domain.use_cases.queries.InsertQueryUseCase
import com.example.domain.use_cases.queries.UpdateQueryUseCase

typealias PhotoItemFlow = Flow<PagingData<PhotoItem>>
typealias SearchQueryFlow = Flow<PagingData<SearchItem>>

typealias AddToFavoritePhotoItemUseCase = AddToFavoritePhotoItemUseCase<PhotoItemFlow>
typealias DeleteFromFavoritePhotoItemUseCase = DeleteFromFavoritePhotoItemUseCase<PhotoItemFlow>
typealias GetFavoritePhotosUseCase = GetFavoritePhotosUseCase<PhotoItemFlow>
typealias GetMetaInfoPhotoSearchUseCase = GetMetaInfoPhotoSearchUseCase<PhotoItemFlow>
typealias GetPhotosUseCase = GetPhotosUseCase<PhotoItemFlow>
typealias GetUserByPhotoUseCase = GetUserByPhotoUseCase<PhotoItemFlow>


typealias DeleteQueryUseCase = DeleteQueryUseCase<SearchQueryFlow>
typealias GetFavoriteQueriesUseCase = GetFavoriteQueriesUseCase<SearchQueryFlow>
typealias GetQueriesUseCase = GetQueriesUseCase<SearchQueryFlow>
typealias InsertQueryUseCase = InsertQueryUseCase<SearchQueryFlow>
typealias UpdateQueryUseCase = UpdateQueryUseCase<SearchQueryFlow>
