package com.fintech.denispok.fintechproject.students

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import com.google.gson.JsonArray

class StudentsViewModel(private val repository: Repository) : ViewModel() {

    fun getStudents(): LiveData<List<Student>> = repository.getStudents()

    fun updateStudents(callback: ResponseCallback<JsonArray>? = null) = repository.updateStudents(callback)

}
