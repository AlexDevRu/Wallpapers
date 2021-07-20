package com.example.domain.use_cases.queries

import com.example.domain.models.SearchItem
import com.example.domain.repositories.local.ISearchQueryRepository

class InsertQueryUseCase(private val searchRepository: ISearchQueryRepository) {
    suspend operator fun invoke(query: SearchItem) = searchRepository.insertQuery(query)
}
