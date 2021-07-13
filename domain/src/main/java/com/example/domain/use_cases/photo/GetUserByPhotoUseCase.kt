package com.example.domain.use_cases.photo

import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.IPhotoRepository

class GetUserByPhotoUseCase<TPhotoFlow>(private val photoRepository: IPhotoRepository<TPhotoFlow>) {
    suspend operator fun invoke(photoItem: PhotoItem) = photoRepository.getUserByPhoto(photoItem)
}
