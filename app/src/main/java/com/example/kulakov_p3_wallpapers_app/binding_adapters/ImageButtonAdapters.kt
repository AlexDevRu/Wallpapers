package com.example.kulakov_p3_wallpapers_app.binding_adapters

import android.widget.ImageButton
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("app:icon")
fun bindDrawable(view: ImageButton, @DrawableRes resId: Int) {
    view.setImageResource(resId)
}