package com.fintech.denispok.fintechproject.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.repository.dao.LectureDao
import com.fintech.denispok.fintechproject.repository.dao.StudentDao
import com.fintech.denispok.fintechproject.repository.dao.TaskDao
import com.google.gson.JsonArray

class Repository private constructor(
    private val lectureDao: LectureDao,
    private val taskDao: TaskDao,
    private val studentDao: StudentDao,
    private val cachePreferences: SharedPreferences,
    private val apiService: ApiService
) {

    companion object {
        const val STUDENTS_CACHE_LIFETIME = 10_000L
        const val STUDENTS_TIMEOUT_KEY = "students_timeout"

        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            lectureDao: LectureDao,
            taskDao: TaskDao,
            studentDao: StudentDao,
            authPreferences: SharedPreferences,
            apiService: ApiService
        ) = instance ?: synchronized(this) {
            instance ?: Repository(lectureDao, taskDao, studentDao, authPreferences, apiService).also { instance = it }
        }
    }

    private val lectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    private val students: MutableLiveData<List<Student>> = MutableLiveData()
    private var token: String = ""
        get() {
            if (field.isEmpty())
                field = cachePreferences.getString("token", "") ?: ""
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
            val response = apiService.getLectures(token).execute()

            if (response.isSuccessful) {
                response.body()?.also {
                    lectureDao.deleteAllLectures()
                    lectureDao.insertLectures(it.lectures)
                    lectures.postValue(lectureDao.getLectures())

                    taskDao.deleteAllTasks()
                    val tasks = mutableListOf<Task>()
                    it.lectures.forEach { lecture ->
                        lecture.tasks?.forEach { task ->
                            task.lectureId = lecture.id
                            tasks.add(task)
                        }
                    }
                    taskDao.insertTasks(tasks)
                }
            }
        }.start()
    }

    fun getStudents(): LiveData<List<Student>> {
        Thread {
            val studentsTimeout = cachePreferences.getLong(STUDENTS_TIMEOUT_KEY, Long.MIN_VALUE)

            if (studentsTimeout > System.currentTimeMillis()) {
                students.postValue(studentDao.getStudents())
            } else {
                updateStudentsNow()
            }
        }.start()

        return students
    }

    fun updateStudents(callback: ResponseCallback<JsonArray>? = null) = Thread {
        updateStudentsNow(callback)
    }.start()

    fun updateStudentsNow(callback: ResponseCallback<JsonArray>? = null) {
        try {
            val response = apiService.getGrades(token).execute()
            callback?.onResponse(response)

            if (response.isSuccessful) {

                response.body()?.also { body ->
                    val grades = body[1].asJsonObject.getAsJsonArray("grades")
                    val students = mutableListOf<Student>()

                    grades.forEach {
                        val jsonStudent = it.asJsonObject
                        students.add(
                            Student(
                                jsonStudent["student_id"].asLong,
                                jsonStudent["student"].asString,
                                jsonStudent["grades"].asJsonArray.last().asJsonObject["mark"].asDouble
                            )
                        )
                    }

                    studentDao.deleteAllStudents()
                    studentDao.insertStudents(students)
                    cachePreferences.edit()
                        .putLong(STUDENTS_TIMEOUT_KEY, System.currentTimeMillis() + STUDENTS_CACHE_LIFETIME)
                        .apply()
                    this@Repository.students.postValue(studentDao.getStudents())
                }
            }
        } catch (t: Throwable) {
            callback?.onFailure(t)
        }
    }

    fun getTasks(lectureId: Int): LiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()
        Thread {
            tasksLiveData.postValue(taskDao.getTasks(lectureId))
        }.start()
        return tasksLiveData
    }

}