package com.fintech.denispok.fintechproject.db

import android.arch.persistence.room.Room
import com.fintech.denispok.fintechproject.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(): Database =
        Room.databaseBuilder(App.instance, Database::class.java, "database")
            .build()

}
