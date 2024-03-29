package com.fintech.denispok.fintechproject.di

import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.ui.auth.AuthViewModelFactory
import com.fintech.denispok.fintechproject.ui.courses.CoursesViewModelFactory
import com.fintech.denispok.fintechproject.ui.courses.rating.RatingViewModelFactory
import com.fintech.denispok.fintechproject.ui.events.EventsViewModelFactory
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModelFactory
import com.fintech.denispok.fintechproject.ui.profile.ProfileViewModelFactory
import com.fintech.denispok.fintechproject.ui.students.StudentsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideLecturesViewModelFactory(repository: Repository): LecturesViewModelFactory =
        LecturesViewModelFactory(repository)

    @Provides
    fun provideStudentsViewModelFactory(repository: Repository): StudentsViewModelFactory =
        StudentsViewModelFactory(repository)

    @Provides
    fun provideAuthViewModelFactory(repository: Repository): AuthViewModelFactory =
        AuthViewModelFactory(repository)

    @Provides
    fun provideProfileViewModelFactory(repository: Repository): ProfileViewModelFactory =
        ProfileViewModelFactory(repository)

    @Provides
    fun provideEventsViewModelFactory(repository: Repository): EventsViewModelFactory =
        EventsViewModelFactory(repository)

    @Provides
    fun provideCoursesViewModelFactory(repository: Repository): CoursesViewModelFactory =
        CoursesViewModelFactory(repository)

    @Provides
    fun provideRatingViewModelFactory(repository: Repository): RatingViewModelFactory =
        RatingViewModelFactory(repository)
}
