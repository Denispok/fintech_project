package com.fintech.denispok.fintechproject.ui.students

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StudentsViewModel(private val repository: Repository) : ViewModel() {

    fun getStudents(): Observable<List<Student>> {
        val observable = repository.getStudents()
        return observable.subscribeOn(Schedulers.io())
            .map { it.filter { student -> student.mark > 20 } }
            .observeOn(AndroidSchedulers.mainThread())
    }

}
