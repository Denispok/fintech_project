package com.fintech.denispok.fintechproject.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.AuthRequestBody
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.db.dao.LectureDao
import com.fintech.denispok.fintechproject.db.dao.StudentDao
import com.fintech.denispok.fintechproject.db.dao.TaskDao
import com.fintech.denispok.fintechproject.db.dao.UserDao
import io.reactivex.Observable

class Repository(
    private val lectureDao: LectureDao,
    private val taskDao: TaskDao,
    private val studentDao: StudentDao,
    private val userDao: UserDao,
    private val cachePreferences: SharedPreferences,
    private val apiService: ApiService
) {

    companion object {
        const val CACHE_LIFETIME = 10_000L
        const val LECTURES_TIMEOUT_KEY = "lectures_timeout"
        const val STUDENTS_TIMEOUT_KEY = "students_timeout"
        const val USER_TIMEOUT_KEY = "user_timeout"
    }

    private val lectures: MutableLiveData<List<Lecture>> = MutableLiveData()
    private val user: MutableLiveData<User> = MutableLiveData()
    var token: String = ""
        get() {
            if (field.isEmpty())
                field = cachePreferences.getString("token", "") ?: ""
            return field
        }
        set(value) {
            field = value
            cachePreferences.edit().putString("token", field).apply()
        }

    fun getLectures(callback: ResponseCallback? = null): LiveData<List<Lecture>> {
        if (lectures.value == null) updateLecturesCache(callback)
        return lectures
    }

    fun updateLecturesCache(callback: ResponseCallback? = null) = Thread {
        val lecturesTimeout = cachePreferences.getLong(LECTURES_TIMEOUT_KEY, Long.MIN_VALUE)

        if (lecturesTimeout > System.currentTimeMillis() || lectures.value == null) {
            lectures.postValue(lectureDao.getLectures())
        }
        if (lecturesTimeout <= System.currentTimeMillis()) {
            updateLecturesFromServer(callback)
        }

    }.start()

    fun updateLecturesFromServer(callback: ResponseCallback? = null) {
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
                    cachePreferences.edit()
                            .putLong(LECTURES_TIMEOUT_KEY, System.currentTimeMillis() + CACHE_LIFETIME)
                            .apply()
                }
            } else {
                callback?.onFailure()
            }
        } catch (t: Throwable) {
            Log.d("REPO", t.message)
            callback?.onFailure(t.message)
        }
    }

    fun getStudents():Observable<List<Student>> {
        return updateStudentsCache()
    }

    fun updateStudentsCache() = Observable.create<List<Student>> {emmiter ->
        val studentsTimeout = cachePreferences.getLong(STUDENTS_TIMEOUT_KEY, Long.MIN_VALUE)

        emmiter.onNext(studentDao.getStudents())

        if (studentsTimeout <= System.currentTimeMillis()) {
            updateStudentsFromServer().subscribe {
                emmiter.onNext(it)
            }
        }
    }

    fun updateStudentsFromServer() = Observable.create<List<Student>> {
        try {
            val response = apiService.getGrades(token).execute()

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
                        .putLong(STUDENTS_TIMEOUT_KEY, System.currentTimeMillis() + CACHE_LIFETIME)
                        .apply()
                    it.onNext(studentDao.getStudents())
                }
            }
        } catch (t: Throwable) {
            it.onError(t)
        }
    }

    fun getTasks(lectureId: Int): LiveData<List<Task>> {
        val tasksLiveData = MutableLiveData<List<Task>>()
        Thread {
            tasksLiveData.postValue(taskDao.getTasks(lectureId))
        }.start()
        return tasksLiveData
    }

    fun getUser(callback: ResponseCallback? = null): LiveData<User> {
        if (user.value == null) updateUserCache(callback)
        return user
    }

    fun updateUserCache(callback: ResponseCallback? = null) = Thread {
        val userTimeout = cachePreferences.getLong(USER_TIMEOUT_KEY, Long.MIN_VALUE)

        if (userTimeout > System.currentTimeMillis() || user.value == null) {
            user.postValue(userDao.getUser())
        }
        if (userTimeout <= System.currentTimeMillis()) {
            updateUserFromServer(callback)
        }

    }.start()

    fun updateUserFromServer(callback: ResponseCallback? = null) {
        try {
            val response = apiService.getUser(token).execute()
            if (response.isSuccessful) {
                response.body()?.also { body ->

                    if (body.status == "Ok") {
                        body.user?.also { user ->

                            userDao.deleteAllUsers()
                            userDao.insertUser(user)
                            cachePreferences.edit()
                                    .putLong(USER_TIMEOUT_KEY, System.currentTimeMillis() + CACHE_LIFETIME)
                                    .apply()
                            this@Repository.user.postValue(userDao.getUser())
                        }
                    } else {
                        callback?.onFailure(body.message)
                    }
                }
            } else {
                callback?.onFailure()
            }
        } catch (t: Throwable) {
            callback?.onFailure(t.message)
        }
    }

    fun authCall(authRequestBody: AuthRequestBody) = apiService.auth(authRequestBody)
}
