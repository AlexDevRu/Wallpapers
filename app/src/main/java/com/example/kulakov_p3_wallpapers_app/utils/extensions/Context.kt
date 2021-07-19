package com.example.kulakov_p3_wallpapers_app.utils.extensions

import android.content.Context
import android.net.ConnectivityManager

val Context.isConnected: Boolean
    get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected == true