package com.example.domain.use_cases.queries

import com.example.domain.models.SearchItem
import com.example.domain.repositories.local.ISearchQueryRepository

class DeleteQueryUseCase<TSearchQueryFlow>(private val searchRepository: ISearchQueryRepository<TSearchQueryFlow>) {
    suspend operator fun invoke(query: SearchItem) = searchRepository.deleteQuery(query)
}
