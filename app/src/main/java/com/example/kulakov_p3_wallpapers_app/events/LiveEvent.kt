package com.example.kulakov_p3_wallpapers_app.events

import androidx.lifecycle.LiveData

class LiveEvent: LiveData<Unit>() {

    fun trigger() {
        value = Unit
    }

}