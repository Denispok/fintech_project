package com.fintech.denispok.fintechproject.di

import com.fintech.denispok.fintechproject.api.RetrofitModule
import com.fintech.denispok.fintechproject.db.DatabaseModule
import com.fintech.denispok.fintechproject.repository.RepositoryModule
import com.fintech.denispok.fintechproject.ui.auth.AuthActivity
import com.fintech.denispok.fintechproject.ui.lectures.LecturesActivity
import com.fintech.denispok.fintechproject.ui.lectures.tasks.TaskActivity
import com.fintech.denispok.fintechproject.ui.profile.ProfileFragment
import com.fintech.denispok.fintechproject.ui.students.StudentsActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, DatabaseModule::class, RepositoryModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: AuthActivity)
    fun inject(activity: StudentsActivity)
    fun inject(activity: LecturesActivity)
    fun inject(activity: TaskActivity)
    fun inject(fragment: ProfileFragment)

}
