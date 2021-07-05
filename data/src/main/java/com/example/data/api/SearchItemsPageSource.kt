package com.example.data.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.database.PhotoDao
import com.example.data.mappers.SearchItemMapper
import com.example.domain.data.SearchItem

/*class SearchItemsPageSource(private val photoDao: PhotoDao): PagingSource<Int, SearchItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        val queries = SearchItemMapper.toModel(photoDao.getQueries())
        return LoadResult.Page(
            data = queries,
            prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
            nextKey = if (queries.isNullOrEmpty()) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SearchItem>): Int? = null
}*/