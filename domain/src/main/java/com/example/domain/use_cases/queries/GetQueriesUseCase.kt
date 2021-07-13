package com.example.domain.use_cases.queries

import com.example.domain.repositories.local.ISearchQueryRepository

class GetQueriesUseCase<TSearchQueryFlow>(private val searchRepository: ISearchQueryRepository<TSearchQueryFlow>) {
    suspend operator fun invoke() = searchRepository.getQueries()
}
