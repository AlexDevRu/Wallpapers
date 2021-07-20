package com.example.domain.use_cases.photo

import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.IPhotoRepository

class AddToFavoritePhotoItemUseCase(private val photoRepository: IPhotoRepository) {
    suspend operator fun invoke(photoItem: PhotoItem) = photoRepository.addToFavoritePhotoItem(photoItem)
}
