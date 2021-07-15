package com.example.data.database.repositories.test

import androidx.paging.PagingSource
import com.example.data.aliases.PhotoItemFlow
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.models.SearchItem
import com.example.domain.models.User
import com.example.domain.repositories.remote.IPhotoApiRepository

/*class FakePhotoRepository: IPhotoApiRepository<PhotoItemFlow> {

    private val photos = mutableListOf<PhotoItem>()

    fun getFavoritePhotos(): PhotoItemFlow {
        val pagingSource = PagingSource<Int, PhotoItem>()
        return photos
    }

    fun addToFavoritePhotoItem(photoItem: PhotoItem) {
        photos.add(photoItem)
    }

    fun deleteFromFavoritePhotoItem(photoItem: PhotoItem) {
        photos.remove(photoItem)
    }

    fun getUserByPhoto(photoItem: PhotoItem, shouldToReturnSuccess: Boolean): Result<User> {
        if(shouldToReturnSuccess)
            return Result.Success(User())
        return Result.Failure(Exception())
    }
}*/