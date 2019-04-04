package com.fintech.denispok.fintechproject.utilities

import android.content.Context
import com.fintech.denispok.fintechproject.lectures.LecturesViewModelFactory
import com.fintech.denispok.fintechproject.repository.DatabaseProvider
import com.fintech.denispok.fintechproject.repository.Repository

object InjectorUtils {

    fun provideProfileViewModelFactory(applicationContext: Context): LecturesViewModelFactory {
        val database = DatabaseProvider.getInstance(applicationContext)
        val authPreferences = applicationContext.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val repository = Repository.getInstance(database.lectureDao(), database.taskDao(), authPreferences)
        return LecturesViewModelFactory(repository)
    }
}
