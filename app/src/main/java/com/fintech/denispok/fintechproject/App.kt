package com.fintech.denispok.fintechproject

import android.app.Application
import com.fintech.denispok.fintechproject.utilities.DaggerInjectorComponent
import com.fintech.denispok.fintechproject.utilities.InjectorComponent
import com.fintech.denispok.fintechproject.utilities.InjectorUtilsModule

class App : Application() {

    lateinit var component: InjectorComponent

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        setup()
    }

    private fun setup() {
        component = DaggerInjectorComponent.builder()
            .injectorUtilsModule(InjectorUtilsModule(applicationContext)).build()
        component.inject(this)
    }
}
