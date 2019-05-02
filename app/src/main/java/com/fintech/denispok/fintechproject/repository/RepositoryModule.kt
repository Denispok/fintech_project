package com.fintech.denispok.fintechproject.repository

import android.content.Context
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.db.Database
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RepositoryModule(private val applicationContext: Context) {

    @Singleton
    @Provides
    fun provideRepository(retrofit: Retrofit, database: Database): Repository {
        val cachePreferences = applicationContext.getSharedPreferences("cache", Context.MODE_PRIVATE)
        val apiService = retrofit.create(ApiService::class.java)
        return Repository(
            database.lectureDao(),
            database.taskDao(),
            database.studentDao(),
            database.userDao(),
            database.eventDao(),
            cachePreferences,
            apiService
        )
    }
}
