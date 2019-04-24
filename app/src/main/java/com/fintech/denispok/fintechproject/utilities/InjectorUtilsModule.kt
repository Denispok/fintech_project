package com.fintech.denispok.fintechproject.utilities

import android.content.Context
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.repository.DatabaseProvider
import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.ui.auth.AuthViewModelFactory
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModelFactory
import com.fintech.denispok.fintechproject.ui.profile.ProfileViewModelFactory
import com.fintech.denispok.fintechproject.ui.students.StudentsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class InjectorUtilsModule(private val applicationContext: Context) {

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

    @Provides
    fun provideLecturesViewModelFactory(): LecturesViewModelFactory =
            LecturesViewModelFactory(provideRepository())

    @Provides
    fun provideStudentsViewModelFactory(): StudentsViewModelFactory =
            StudentsViewModelFactory(provideRepository())

    @Provides
    fun provideAuthViewModelFactory(): AuthViewModelFactory =
            AuthViewModelFactory(provideRepository())

    @Provides
    fun provideProfileViewModelFactory(): ProfileViewModelFactory =
            ProfileViewModelFactory(provideRepository())
}
