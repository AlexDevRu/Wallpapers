package com.example.data.repositories.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.domain.aliases.SearchQueryFlow
import com.example.data.database.dao.SearchQueryDao
import com.example.data.mappers.SearchItemMapper
import com.example.domain.models.SearchItem
import com.example.domain.repositories.local.ISearchQueryRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchQueryRepository @Inject constructor(
    private val searchQueryDao: SearchQueryDao
): ISearchQueryRepository {

    companion object {
        const val QUERY_PAGE_SIZE = 20
    }

    override suspend fun getQueries(): SearchQueryFlow {
        return Pager(PagingConfig(QUERY_PAGE_SIZE)) {
            searchQueryDao.getQueries()
        }.flow.map { item ->
            item.map { SearchItemMapper.toModel(it) }
        }
    }

    override suspend fun getFavoriteQueries(): SearchQueryFlow {
        return Pager(PagingConfig(QUERY_PAGE_SIZE)) {
            searchQueryDao.getFavoriteQueries()
        }.flow.map { item ->
            item.map { SearchItemMapper.toModel(it) }
        }
    }

    override suspend fun insertQuery(query: SearchItem) {
        return searchQueryDao.create(SearchItemMapper.fromModel(query))
    }

    override suspend fun updateQuery(query: SearchItem) {
        return searchQueryDao.update(SearchItemMapper.fromModel(query))
    }

    override suspend fun deleteQuery(query: SearchItem) {
        return searchQueryDao.delete(SearchItemMapper.fromModel(query))
    }
}