package com.example.kulakov_p3_wallpapers_app.view_models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import com.example.kulakov_p3_wallpapers_app.R
import com.squareup.picasso.Picasso


@BindingAdapter("query")
fun setQuery(searchView: SearchView, queryText: String?) {
    searchView.setQuery(queryText, false)
}

@BindingAdapter("queryTextListener")
fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener?) {
    searchView.setOnQueryTextListener(listener)
}

@BindingAdapter("app:isLoading")
fun bindIsLoading(view: View, isLoading: Boolean) {
    view.visibility = if (isLoading) View.VISIBLE else View.GONE
}

@BindingAdapter("app:photo")
fun bindPhoto(view: ImageView, photo: Any?) {
    if(photo is Bitmap) view.setImageBitmap(photo)
    else if(photo is Drawable) view.setImageDrawable(photo)
    else if(photo is String) {
        Picasso.get()
            .load(photo)
            .placeholder(R.drawable.no_photo)
            .error(R.drawable.no_photo)
            .into(view)
    }
}

@BindingAdapter("app:icon")
fun bindDrawable(view: ImageButton, @DrawableRes resId: Int) {
    view.setImageResource(resId)
}