package com.fintech.denispok.fintechproject

import android.app.Application
import com.fintech.denispok.fintechproject.db.DatabaseModule
import com.fintech.denispok.fintechproject.di.ApplicationComponent
import com.fintech.denispok.fintechproject.di.DaggerApplicationComponent
import com.fintech.denispok.fintechproject.repository.RepositoryModule

class App : Application() {

    companion object {
        lateinit var applicationComponent: ApplicationComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .repositoryModule(RepositoryModule(applicationContext))
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }
}
