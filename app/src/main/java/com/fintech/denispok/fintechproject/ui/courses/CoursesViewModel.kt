package com.fintech.denispok.fintechproject.ui.courses

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Course
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CoursesViewModel(private val repository: Repository) : ViewModel() {

    fun getCourses(): Observable<List<Course>> =
        repository.getCourses()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
