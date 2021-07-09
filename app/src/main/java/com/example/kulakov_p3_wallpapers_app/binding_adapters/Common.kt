package com.example.kulakov_p3_wallpapers_app.binding_adapters

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion

@BindingConversion
fun convertBooleanToVisibility(visible: Boolean): Int {
    if(visible) return View.VISIBLE
    return View.GONE
}
