package org.tawhid.readout

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.tawhid.readout.core.di.initKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@BaseApplication)
        }
    }
}