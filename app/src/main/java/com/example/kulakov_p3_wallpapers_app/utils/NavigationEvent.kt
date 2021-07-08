package com.example.kulakov_p3_wallpapers_app.utils

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

class NavigationEvent(
    var direction: NavDirections? = null,
    var extras: FragmentNavigator.Extras? = null
)