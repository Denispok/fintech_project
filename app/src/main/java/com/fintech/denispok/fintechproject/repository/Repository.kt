package com.fintech.denispok.fintechproject.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.SharedPreferences
import android.util.Log
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.AuthRequestBody
import com.fintech.denispok.fintechproject.api.entity.*
import com.fintech.denispok.fintechproject.db.dao.*
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

class Repository(
    private val lectureDao: LectureDao,
    private val taskDao: TaskDao,
    private val studentDao: StudentDao,
    private val userDao: UserDao,
    private val eventDao: EventDao,
    private val courseDao: CourseDao,
    private val cachePreferences: SharedPreferences,
    private val apiService: ApiService
) {

    companion object {
        const val CACHE_LIFETIME = 10_000L
        const val LECTURES_TIMEOUT_KEY = "lectures_timeout"
        const val STUDENTS_TIMEOUT_KEY = "students_timeout"
        const val USER_TIMEOUT_KEY = "user_timeout"
        const val EVENTS_TIMEOUT_KEY = "events_timeout"
        const val COURSES_TIMEOUT_KEY = "courses_timeout"
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

    fun getStudents() = Observable.create<List<Student>> { emitter ->
        emitter.onNext(studentDao.getStudents())

        val studentsTimeout = cachePreferences.getLong(STUDENTS_TIMEOUT_KEY, Long.MIN_VALUE)

        if (studentsTimeout <= System.currentTimeMillis()) {
            updateStudentsFromServer().subscribe({
                emitter.onNext(it)
                emitter.onComplete()
            }, {
                emitter.onError(it)
            })
        } else {
            emitter.onComplete()
        }
    }

    fun updateStudentsFromServer() = Single.fromCallable<List<Student>> {
        val response = apiService.getGrades(token).execute()

        if (response.isSuccessful) {

            val body = response.body()!!
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

            return@fromCallable studentDao.getStudents()
        } else {
            throw (Throwable("Connection error"))
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

    fun getActiveEvents() = Observable.create<List<Event>> { emitter ->
        emitter.onNext(eventDao.getActiveEvents())

        val eventsTimeout = cachePreferences.getLong(EVENTS_TIMEOUT_KEY, Long.MIN_VALUE)

        if (eventsTimeout <= System.currentTimeMillis()) {
            updateEventsFromServer().subscribe({
                emitter.onNext(eventDao.getActiveEvents())
                emitter.onComplete()
            }, {
                emitter.onError(it)
            })
        } else {
            emitter.onComplete()
        }
    }

    fun updateEventsFromServer() = Completable.fromCallable {
        val response = apiService.getEvents(token).execute()

        if (response.isSuccessful) {
            val body = response.body()!!
            eventDao.deleteAllEvents()
            var id = 0
            eventDao.insertEvents(body.active.onEach {
                it.isPassed = false
                it.id = id++
            })
            eventDao.insertEvents(body.archive.onEach {
                it.isPassed = true
                it.id = id++
            })
            cachePreferences.edit()
                .putLong(EVENTS_TIMEOUT_KEY, System.currentTimeMillis() + CACHE_LIFETIME)
                .apply()
        } else {
            throw Throwable("Connection error")
        }
    }

    fun getCourses() = Observable.create<List<Course>> { emitter ->
        emitter.onNext(courseDao.getCourses())

        val eventsTimeout = cachePreferences.getLong(COURSES_TIMEOUT_KEY, Long.MIN_VALUE)

        if (eventsTimeout <= System.currentTimeMillis()) {
            updateCoursesFromServer().subscribe({
                emitter.onNext(courseDao.getCourses())
                emitter.onComplete()
            }, {
                emitter.onError(it)
            })
        } else {
            emitter.onComplete()
        }
    }

    fun updateCoursesFromServer() = Completable.fromCallable {
        val response = apiService.getConnections(token).execute()

        if (response.isSuccessful) {
            val body = response.body()!!
            courseDao.deleteAllCourses()
            courseDao.insertCourses(body.courses)
            cachePreferences.edit()
                .putLong(COURSES_TIMEOUT_KEY, System.currentTimeMillis() + CACHE_LIFETIME)
                .apply()
        } else {
            throw Throwable("Connection error")
        }
    }

    fun authCall(authRequestBody: AuthRequestBody) = apiService.auth(authRequestBody)
}
