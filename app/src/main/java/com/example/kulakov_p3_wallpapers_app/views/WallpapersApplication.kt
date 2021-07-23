package com.example.kulakov_p3_wallpapers_app.views

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class WallpapersApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }
}
