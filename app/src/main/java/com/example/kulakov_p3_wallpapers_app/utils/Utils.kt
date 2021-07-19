package com.example.kulakov_p3_wallpapers_app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import java.io.File
import java.io.FileOutputStream
import java.net.URL


object Utils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    suspend fun getBitmapByUrl(link: String): Bitmap {
        val url = URL(link)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    suspend fun getBitmapByLocalPath(path: String): Bitmap {
        val image = File(path)
        val bmOptions = BitmapFactory.Options()
        return BitmapFactory.decodeFile(image.absolutePath, bmOptions)
    }

    suspend fun saveBitmap(image: Bitmap, folder: File, fileName: String): String {
        if(!folder.exists())
            folder.mkdirs()

        val file = File(folder, fileName)
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        return file.absolutePath
    }

    suspend fun saveFavoritePhoto(context: Context, photoItem: PhotoItem): String? {
        if(photoItem.localPhotoPath != null)
            return saveBitmap(
                getBitmapByLocalPath(photoItem.localPhotoPath!!),
                getFavoriteImagesFolder(context),
                photoItem.id
            )

        if(isNetworkConnected(context))
            return saveBitmap(
                getBitmapByUrl(photoItem.regular!!),
                getFavoriteImagesFolder(context),
                photoItem.id
            )

        return null
    }

    suspend fun saveFavoriteUser(context: Context, user: User): String? {
        if(user.localPhotoPath != null)
            return saveBitmap(
                getBitmapByLocalPath(user.localPhotoPath!!),
                getFavoriteImagesFolder(context),
                user.id
            )

        if(isNetworkConnected(context))
            return saveBitmap(
                getBitmapByUrl(user.photoUrl!!),
                getFavoriteImagesFolder(context),
                user.id
            )

        return null
    }

    private fun getFavoriteImagesFolder(context: Context): File {
        return File(context.filesDir, "images")
    }

    private fun getFavoriteUsersFolder(context: Context): File {
        return File(context.filesDir, "users")
    }
}