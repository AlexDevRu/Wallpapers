package com.example.domain.use_cases.photo

import com.example.domain.repositories.remote.IPhotoApiRepository

class GetPhotosUseCase(private val photoApiRepository: IPhotoApiRepository) {
    suspend operator fun invoke(query: String?, isShouldSaveQuery: Boolean) = photoApiRepository.getPhotos(query, isShouldSaveQuery)
}
