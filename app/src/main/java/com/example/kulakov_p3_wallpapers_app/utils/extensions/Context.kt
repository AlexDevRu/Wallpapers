package com.example.kulakov_p3_wallpapers_app.utils.extensions

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.kulakov_p3_wallpapers_app.R

val Context.loadingDialog: Dialog
    get() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Загрузка").setView(R.layout.item_loading)
        return builder.create()
    }