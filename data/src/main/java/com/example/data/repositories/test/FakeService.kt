package com.example.data.repositories.test

import com.example.data.api.PhotoApiService
import com.example.data.api.responses.PhotoItemApiResponse
import com.example.data.api.responses.PhotoSearchByKeywordResult
import com.example.data.mappers.PhotoResponseMapper
import com.example.domain.models.PhotoItem

class FakeService: PhotoApiService {

    lateinit var dataSet: List<Pair<String, PhotoItem>>

    override suspend fun getPhotos(
        page: Int,
        query: String?,
        perPage: Int,
        clientId: String
    ): PhotoSearchByKeywordResult {

        val result = dataSet.filter { it.first == query }
        if(((page - 1) * perPage) >= result.size) {
            return PhotoSearchByKeywordResult(0, errors = listOf("end of pages"))
        }
        return PhotoSearchByKeywordResult(
            result.size,
            PhotoResponseMapper.fromModel(result.map { it.second }.subList(page * perPage, result.size))
        )
    }

    override suspend fun getPhotos(
        page: Int,
        perPage: Int,
        clientId: String
    ): List<PhotoItemApiResponse> {
        return PhotoResponseMapper.fromModel(dataSet.map { it.second })
    }
}
