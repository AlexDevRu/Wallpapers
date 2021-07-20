package com.example.domain.use_cases.photo

import com.example.domain.repositories.local.IPhotoRepository

class GetFavoritePhotosUseCase(private val photoRepository: IPhotoRepository) {
    suspend operator fun invoke() = photoRepository.getFavoritePhotos()
}
