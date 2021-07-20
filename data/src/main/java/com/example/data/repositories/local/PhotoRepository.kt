package com.example.data.repositories.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.example.domain.aliases.PhotoItemFlow
import com.example.data.database.dao.PhotoDao
import com.example.data.mappers.PhotoItemMapper
import com.example.data.mappers.UserMapper
import com.example.domain.common.Result
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import com.example.domain.repositories.local.IPhotoRepository
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoDao: PhotoDao
): IPhotoRepository {

    companion object {
        const val PHOTO_PAGE_SIZE = 10
    }

    override suspend fun getFavoritePhotos(): PhotoItemFlow {
        return Pager(PagingConfig(PHOTO_PAGE_SIZE)) {
            photoDao.getPhotos()
        }.flow.map { item ->
            item.map { PhotoItemMapper.toModel(it) }
        }
    }

    override suspend fun addToFavoritePhotoItem(photoItem: PhotoItem) {
        return photoDao.insertPhoto(PhotoItemMapper.fromModel(photoItem), UserMapper.fromModel(photoItem.user!!))
    }

    override suspend fun deleteFromFavoritePhotoItem(photoItem: PhotoItem) {
        return photoDao.deletePhoto(PhotoItemMapper.fromModel(photoItem), UserMapper.fromModel(photoItem.user!!))
    }

    override suspend fun getUserByPhoto(photoItem: PhotoItem): Result<User> {
        val userFromDb = photoDao.getUserById(photoItem.user!!.id)
        if(userFromDb != null)
            return Result.Success(UserMapper.toModel(userFromDb))
        return Result.Failure(Exception("user is not found"))
    }
}
