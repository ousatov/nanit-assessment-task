package com.usatov.nanithometask

import android.app.Application
import com.usatov.nanithometask.core.di.AppInitEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NanitApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val logger =
            EntryPointAccessors.fromApplication(this, AppInitEntryPoint::class.java).logger()
        logger.init()
    }
}