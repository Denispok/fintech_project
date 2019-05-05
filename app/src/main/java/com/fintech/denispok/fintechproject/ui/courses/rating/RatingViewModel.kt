package com.fintech.denispok.fintechproject.ui.courses.rating

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class RatingViewModel(private val repository: Repository) : ViewModel() {

    fun getCurrentStudent(): Observable<Student> {
        return repository.getStudents()
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .zipWith(
                repository.getUser().subscribeOn(Schedulers.io()),
                BiFunction { students: List<Student>, user: User -> students.first { it.id == user.id } })
            .observeOn(AndroidSchedulers.mainThread())
    }
}
