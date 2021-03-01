package com.d.beteasier

import android.app.Application
import com.d.beteasier.util.Koin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(Koin.appModule)
        }
    }
}