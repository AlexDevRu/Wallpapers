package com.example.kulakov_p3_wallpapers_app.views.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.kulakov_p3_wallpapers_app.utils.ConnectionLiveData

abstract class BaseFragment<TBinding: ViewDataBinding>(
    @LayoutRes private val layout: Int
): Fragment(layout) {

    protected lateinit var binding: TBinding

    protected lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        connectionLiveData = ConnectionLiveData(requireContext())
        return binding.root
    }
}
