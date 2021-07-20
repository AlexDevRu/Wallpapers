package com.example.domain.use_cases.photo

import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.IPhotoRepository

class DeleteFromFavoritePhotoItemUseCase(private val photoRepository: IPhotoRepository) {
    suspend operator fun invoke(photoItem: PhotoItem) = photoRepository.deleteFromFavoritePhotoItem(photoItem)
}
