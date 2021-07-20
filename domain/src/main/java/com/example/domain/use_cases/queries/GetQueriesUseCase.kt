package com.example.domain.use_cases.queries

import com.example.domain.repositories.local.ISearchQueryRepository

class GetQueriesUseCase(private val searchRepository: ISearchQueryRepository) {
    suspend operator fun invoke() = searchRepository.getQueries()
}
