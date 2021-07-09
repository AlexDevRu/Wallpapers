package com.example.kulakov_p3_wallpapers_app.binding_adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.example.kulakov_p3_wallpapers_app.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


@BindingAdapter(value = ["app:photo", "app:startTransition", "app:resizeWidth", "app:resizeHeight"], requireAll = false)
fun bindPhoto(view: ImageView, photo: Any?, startTransition: (() -> Unit)?, resizeWidth: Int?, resizeHeight: Int?) {
    if(photo is Bitmap) view.setImageBitmap(photo)
    else if(photo is Drawable) view.setImageDrawable(photo)
    else if(photo is String) {
        Picasso.get().load(photo)
            .placeholder(R.drawable.no_photo)
            .error(R.drawable.no_photo)
            .into(view, object: Callback {
                override fun onSuccess() {
                    //startTransition?.invoke()
                }

                override fun onError(e: Exception?) {
                    //startTransition?.invoke()
                }
            })
    }
}

@BindingAdapter("app:colorIcon")
fun bindPhoto(view: ImageView, @ColorRes colorIcon: Int) {
    view.setColorFilter(colorIcon)
}