package com.knu.retastylog

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TastyLogApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
