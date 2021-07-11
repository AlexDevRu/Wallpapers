package com.example.kulakov_p3_wallpapers_app.events

import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigator

class NavigationEvent(
    var direction: NavDirections? = null,
    var extras: FragmentNavigator.Extras? = null
)