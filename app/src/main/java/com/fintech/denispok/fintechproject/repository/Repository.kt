package com.fintech.denispok.fintechproject.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.repository.dao.LectureDao
import com.fintech.denispok.fintechproject.repository.dao.TaskDao

class Repository private constructor(
    private val lectureDao: LectureDao,
    private val taskDao: TaskDao,
    private val authPreferences: SharedPreferences
) {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(lectureDao: LectureDao, taskDao: TaskDao, authPreferences: SharedPreferences) =
            instance ?: synchronized(this) {
                instance ?: Repository(lectureDao, taskDao, authPreferences).also { instance = it }
            }
    }

    private val lectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    private var token: String = ""
        get() {
            if (field.isEmpty())
                field = authPreferences.getString("token", "") ?: ""
            return field
        }

    fun getLectures(): LiveData<List<Lecture>> {
        if (lectures.value == null) {
            Thread {
                val cachedLectures = lectureDao.getLectures()

                if (cachedLectures.isEmpty()) {
                    updateLectures()
                } else {
                    lectures.postValue(cachedLectures)
                }
            }.start()
        }
        return lectures
    }

    fun updateLectures() {
        Thread {
            val retrofit = RetrofitProvider.getInstance()
            val apiService = retrofit.create(ApiService::class.java)
            try {
                val response = apiService.getLectures(token).execute()

                if (response.isSuccessful) {
                    response.body()?.also {
                        lectureDao.deleteAllLectures()
                        lectureDao.insertLectures(it.lectures)
                        lectures.postValue(lectureDao.getLectures())

                        taskDao.deleteAllTasks()
                        val tasks = mutableListOf<Task>()
                        it.lectures.forEach { lecture ->
                            lecture.tasks.forEach { task ->
                                task.lectureId = lecture.id
                                tasks.add(task)
                            }
                        }
                        taskDao.insertTasks(tasks)
                    }
                }
            } catch (t: Throwable) {
                Log.d("REPO", t.message)
            }
        }.start()
    }

    fun getTasks(lectureId: Int): LiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()
        Thread {
            tasksLiveData.postValue(taskDao.getTasks(lectureId))
        }.start()
        return tasksLiveData
    }

}
