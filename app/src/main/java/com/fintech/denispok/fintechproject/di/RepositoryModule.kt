package com.fintech.denispok.fintechproject.di

import android.content.Context
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.repository.DatabaseProvider
import com.fintech.denispok.fintechproject.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideRepository(): Repository {
        val database = DatabaseProvider.database
        val cachePreferences = applicationContext.getSharedPreferences("cache", Context.MODE_PRIVATE)
        val retrofit = RetrofitProvider.getInstance()
        val apiService = retrofit.create(ApiService::class.java)
        return Repository.getInstance(
            database.lectureDao(),
            database.taskDao(),
            database.studentDao(),
            database.userDao(),
            cachePreferences,
            apiService
        )
    }
}