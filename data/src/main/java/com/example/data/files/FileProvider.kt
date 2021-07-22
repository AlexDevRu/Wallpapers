package com.example.data.files

import android.Manifest
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import com.example.domain.utils.IFileProvider
import java.io.File
import javax.inject.Inject


class FileProvider @Inject constructor(private val app: Application): IFileProvider {
    private val BASE_DIR = app.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    private val IMAGES_DIR = "photos"
    private val USERS_DIR = "users"

    override fun getPhotoItemFilePath(photoItem: PhotoItem): String? {
        val file = File(BASE_DIR, IMAGES_DIR + File.separator + "${photoItem.id}.jpg")
        return if(file.exists()) file.absolutePath else null
    }

    override fun getUserFilePath(user: User): String? {
        val file = File(BASE_DIR, USERS_DIR + File.separator + "${user.id}.jpg")
        return if(file.exists()) file.absolutePath else null
    }

    private suspend fun downloadBitmapAndSave(link: String, fileName: String, dir: String) {
        val downloadManager = app.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val uri = Uri.parse(link)
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setAllowedOverRoaming(true)
            .setMimeType("image/jpeg")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            .setDestinationInExternalFilesDir(app, Environment.DIRECTORY_PICTURES, File.separator + dir + File.separator + fileName + ".jpg")

        downloadManager.enqueue(request)
    }

    /*private suspend fun downloadBitmapAndSave(url: String, file: String) {
        val url = URL(url)
        var count = 0

        val connection = url.openConnection()
        connection.connect()

        // input stream to read file - with 8k buffer
        val input = BufferedInputStream(url.openStream(), 8192)

        // Output stream to write file
        val output = FileOutputStream(file)
        val data = ByteArray(1024)

        var total: Long = 0
        while (input.read(data).also { count = it } != -1) {
            total += count

            // writing data to file
            output.write(data, 0, count)
        }

        // flushing output
        output.flush()

        // closing streams
        output.close()
        input.close()
    }*/

    override suspend fun savePhoto(photoItem: PhotoItem) {
        if(getPhotoItemFilePath(photoItem) != null)
            return
        //downloadBitmapAndSave(photoItem.regular!!, getPhotoItemFilePath(photoItem)!!)
        downloadBitmapAndSave(photoItem.regular!!, photoItem.id, IMAGES_DIR)
    }

    override suspend fun saveUser(user: User) {
        if(getUserFilePath(user) != null)
            return
        //downloadBitmapAndSave(user.photoUrl!!, getUserFilePath(user)!!)
        downloadBitmapAndSave(user.photoUrl!!, user.id, USERS_DIR)
    }

    override fun deletePhoto(photoItem: PhotoItem) {
        val file = File(BASE_DIR, IMAGES_DIR + File.separator + "${photoItem.id}.jpg")
        if(file.exists())
            file.delete()
    }

    override fun deleteUser(user: User) {
        val file = File(BASE_DIR, USERS_DIR + File.separator + "${user.id}.jpg")
        if(file.exists())
            file.delete()
    }

    override fun checkStoragePermissions(): Boolean {
        return ContextCompat.checkSelfPermission(app, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(app, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }
}