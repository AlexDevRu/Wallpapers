package com.example.domain.use_cases.photo

import com.example.domain.models.PhotoItem
import com.example.domain.repositories.local.IPhotoRepository

class DeleteFromFavoritePhotoItemUseCase<TPhotoFlow>(private val photoRepository: IPhotoRepository<TPhotoFlow>) {
    suspend operator fun invoke(photoItem: PhotoItem) = photoRepository.deleteFromFavoritePhotoItem(photoItem)
}
