package com.picpay.desafio.android.common.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.module.Module

@KoinApiExtension
abstract class FeatureModuleInitializer : KoinComponent, AppCompatActivity() {

    abstract val module: Module

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadModule()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadModule()
    }

    private fun loadModule() = getKoin().loadModules(listOf(module))

    private fun unloadModule() = getKoin().unloadModules(listOf(module))
}
