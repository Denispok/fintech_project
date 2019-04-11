package com.fintech.denispok.fintechproject.repository

import android.arch.persistence.room.Room
import com.fintech.denispok.fintechproject.App

object DatabaseProvider {

    val database by lazy {
        Room.databaseBuilder(App.instance, Database::class.java, "database")
                .build()
    }
}
