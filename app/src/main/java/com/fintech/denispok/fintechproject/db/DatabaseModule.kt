package com.fintech.denispok.fintechproject.db

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideDatabase(): Database =
        Room.databaseBuilder(applicationContext, Database::class.java, "database")
            .build()

}
