package com.example.domain.use_cases.photo

import com.example.domain.repositories.local.IPhotoRepository

class GetFavoritePhotosUseCase<TPhotoFlow>(private val photoRepository: IPhotoRepository<TPhotoFlow>) {
    suspend operator fun invoke() = photoRepository.getFavoritePhotos()
}
