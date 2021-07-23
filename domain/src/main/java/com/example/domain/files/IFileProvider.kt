package com.example.domain.files

import com.example.domain.models.PhotoItem
import com.example.domain.models.User

interface IFileProvider {
    suspend fun savePhoto(photoItem: PhotoItem)
    suspend fun saveUser(user: User)

    fun getPhotoItemFilePath(photoItem: PhotoItem): String?
    fun getUserFilePath(user: User): String?

    fun deletePhoto(photoItem: PhotoItem)
    fun deleteUser(user: User)

    fun checkStoragePermissions(): Boolean
}