package com.fintech.denispok.fintechproject.ui.students

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.repository.Repository

class StudentsViewModel(private val repository: Repository) : ViewModel() {

    fun getStudents(callback: StudentsUpdateCallback? = null): LiveData<List<Student>> =
        repository.getStudents(callback)

    fun updateStudentsCache(callback: StudentsUpdateCallback? = null) = repository.updateStudentsCache(callback)

}