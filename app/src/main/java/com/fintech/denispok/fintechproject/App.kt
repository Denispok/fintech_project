package com.fintech.denispok.fintechproject

import android.app.Application
import com.fintech.denispok.fintechproject.di.ApplicationComponent
import com.fintech.denispok.fintechproject.di.DaggerApplicationComponent
import com.fintech.denispok.fintechproject.repository.RepositoryModule

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        applicationComponent = DaggerApplicationComponent.builder()
            .repositoryModule(RepositoryModule(applicationContext))
            .build()
    }
}
