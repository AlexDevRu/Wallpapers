package com.example.domain.use_cases.photo

import com.example.domain.repositories.remote.IPhotoApiRepository

class GetPhotosUseCase<TPhotoFlow>(private val photoApiRepository: IPhotoApiRepository<TPhotoFlow>) {
    suspend operator fun invoke(query: String?) = photoApiRepository.getPhotos(query)
}
