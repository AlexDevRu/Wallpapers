package com.example.domain.use_cases.queries

import com.example.domain.repositories.local.ISearchQueryRepository

class GetFavoriteQueriesUseCase(private val searchRepository: ISearchQueryRepository) {
    suspend operator fun invoke() = searchRepository.getFavoriteQueries()
}