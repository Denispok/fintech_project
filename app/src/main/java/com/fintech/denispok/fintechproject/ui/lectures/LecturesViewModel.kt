package com.fintech.denispok.fintechproject.ui.lectures

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.repository.ResponseCallback

class LecturesViewModel(private val repository: Repository) : ViewModel() {

    fun getLectures(callback: ResponseCallback? = null): LiveData<List<Lecture>> = repository.getLectures(callback)

    fun getTasks(lectureId: Int): LiveData<List<Task>> = repository.getTasks(lectureId)

    fun updateLectures(callback: ResponseCallback? = null) = repository.updateLecturesFromServer(callback)

}
