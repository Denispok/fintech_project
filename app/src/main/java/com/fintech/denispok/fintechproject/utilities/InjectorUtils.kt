package com.fintech.denispok.fintechproject.utilities

import android.content.Context
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModelFactory
import com.fintech.denispok.fintechproject.repository.DatabaseProvider
import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.ui.students.StudentsViewModelFactory

object InjectorUtils {

    fun provideRepository(applicationContext: Context): Repository {
        val database = DatabaseProvider.database
        val cachePreferences = applicationContext.getSharedPreferences("cache", Context.MODE_PRIVATE)
        val retrofit = RetrofitProvider.getInstance()
        val apiService = retrofit.create(ApiService::class.java)
        return Repository.getInstance(
                database.lectureDao(),
                database.taskDao(),
                database.studentDao(),
                cachePreferences,
                apiService
        )
    }

    fun provideProfileViewModelFactory(applicationContext: Context): LecturesViewModelFactory =
            LecturesViewModelFactory(provideRepository(applicationContext))

    fun provideStudentsViewModelFactory(applicationContext: Context): StudentsViewModelFactory =
            StudentsViewModelFactory(provideRepository(applicationContext))
}
