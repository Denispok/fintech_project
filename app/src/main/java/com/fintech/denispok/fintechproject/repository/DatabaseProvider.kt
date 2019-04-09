package com.fintech.denispok.fintechproject.repository

import android.arch.persistence.room.Room
import android.content.Context

object DatabaseProvider {
    @Volatile
    private var instance: Database? = null

    fun getInstance(applicationContext: Context) =
        instance ?: synchronized(this) {
            instance ?: Room
                .databaseBuilder(applicationContext, Database::class.java, "database")
                .build()
                .also { instance = it }
        }
}
