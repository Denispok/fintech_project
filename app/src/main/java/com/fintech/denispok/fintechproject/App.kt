package com.fintech.denispok.fintechproject

import android.app.Application
import android.content.Context
import com.fintech.denispok.fintechproject.db.DatabaseModule
import com.fintech.denispok.fintechproject.di.ApplicationComponent
import com.fintech.denispok.fintechproject.di.DaggerApplicationComponent
import com.fintech.denispok.fintechproject.repository.RepositoryModule
import io.reactivex.plugins.RxJavaPlugins

class App : Application() {

    companion object {
        lateinit var applicationContext: Context
            private set
        lateinit var applicationComponent: ApplicationComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()

        App.applicationContext = applicationContext

        RxJavaPlugins.setErrorHandler { }

        applicationComponent = DaggerApplicationComponent.builder()
            .repositoryModule(RepositoryModule(applicationContext))
            .databaseModule(DatabaseModule(applicationContext))
            .build()
    }
}
