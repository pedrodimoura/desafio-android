package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.common.di.networkModule
import com.picpay.desafio.android.features.contacts.di.mainFeatureModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PicPayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PicPayApplication)
            modules(
                networkModule,
                mainFeatureModule
            )
        }
    }

}