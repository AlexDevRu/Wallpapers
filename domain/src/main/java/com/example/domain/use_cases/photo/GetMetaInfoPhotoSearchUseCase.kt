package com.example.domain.use_cases.photo

import com.example.domain.repositories.remote.IPhotoApiRepository

class GetMetaInfoPhotoSearchUseCase<TPhotoFlow>(private val photoApiRepository: IPhotoApiRepository<TPhotoFlow>) {
    operator fun invoke() = photoApiRepository.getSearchItem()
}
