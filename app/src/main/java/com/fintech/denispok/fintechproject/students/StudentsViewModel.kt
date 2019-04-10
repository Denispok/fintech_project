package com.fintech.denispok.fintechproject.students

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.repository.Repository

class StudentsViewModel(private val repository: Repository) : ViewModel() {

    fun getStudents(callback: StudentsUpdateCallback? = null): LiveData<List<Student>> =
        repository.getStudents(callback)

    fun updateStudents(callback: StudentsUpdateCallback? = null) = repository.updateStudents(callback)

}
