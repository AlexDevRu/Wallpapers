package com.example.kulakov_p3_wallpapers_app.views.fragments.photo_detail

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.navigation.fragment.navArgs
import com.example.kulakov_p3_wallpapers_app.R
import com.example.kulakov_p3_wallpapers_app.databinding.FragmentWebviewBinding
import com.example.kulakov_p3_wallpapers_app.views.fragments.base.BaseFragment


class WebViewFragment: BaseFragment<FragmentWebviewBinding>
    (R.layout.fragment_webview) {

    private val args: WebViewFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.url = args.url
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                binding.loadProgress = progress
            }
        }
    }
}
