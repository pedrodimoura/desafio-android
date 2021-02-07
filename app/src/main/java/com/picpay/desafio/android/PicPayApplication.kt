package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.common.di.analyticsModule
import com.picpay.desafio.android.common.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startDI()
    }

    private fun startDI() {
        startKoin {
            androidContext(this@PicPayApplication)
            modules(networkModule, analyticsModule)
        }
    }
}
