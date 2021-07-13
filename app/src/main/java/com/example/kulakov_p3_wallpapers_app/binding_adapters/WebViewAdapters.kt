package com.example.kulakov_p3_wallpapers_app.binding_adapters

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter

@BindingAdapter("url")
fun bindUrl(webView: WebView, url: String) {
    webView.settings.javaScriptEnabled =  true
    webView.loadUrl(url)
}