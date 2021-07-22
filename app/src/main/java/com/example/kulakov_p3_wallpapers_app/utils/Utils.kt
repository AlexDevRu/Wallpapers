package com.example.kulakov_p3_wallpapers_app.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File
import java.net.URL


object Utils {
    suspend fun getBitmapByUrl(link: String): Bitmap {
        val url = URL(link)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    fun getBitmapByLocalPath(path: String): Bitmap? {
        val image = File(path)
        if(!image.exists())
            return null
        return BitmapFactory.decodeFile(image.absolutePath)
    }
}
