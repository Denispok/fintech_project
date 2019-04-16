package com.fintech.denispok.fintechproject.ui.lectures

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.repository.Repository

class LecturesViewModel(private val repository: Repository) : ViewModel() {

    fun getLectures(): LiveData<List<Lecture>> = repository.getLectures()

    fun getTasks(lectureId: Int): LiveData<List<Task>> = repository.getTasks(lectureId)

    fun updateLectures() = repository.updateLectures()

}
