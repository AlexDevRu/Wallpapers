package com.example.kulakov_p3_wallpapers_app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.domain.models.PhotoItem
import com.example.domain.models.User
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object Utils {

    fun getBitmap(link: String): Bitmap {
        val url = URL(link)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    fun saveBitmap(context: Context, image: Bitmap, folder: File, fileName: String): String {
        if(!folder.exists())
            folder.mkdirs()

        val file = File(folder, fileName)
        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        return file.absolutePath
    }

    fun saveFavoritePhoto(context: Context, photoItem: PhotoItem): String {
        return saveBitmap(context, getBitmap(photoItem.regular!!), getFavoriteImagesFolder(context), photoItem.id)

    }

    fun saveFavoriteUser(context: Context, user: User): String {
        return saveBitmap(context, getBitmap(user.photoUrl!!), getFavoriteUsersFolder(context), user.id)
    }

    private fun getFavoriteImagesFolder(context: Context): File {
        return File(context.filesDir, "images")
    }

    private fun getFavoriteUsersFolder(context: Context): File {
        return File(context.filesDir, "users")
    }
}