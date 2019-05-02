package com.fintech.denispok.fintechproject.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.db.dao.LectureDao
import com.fintech.denispok.fintechproject.db.dao.StudentDao
import com.fintech.denispok.fintechproject.db.dao.TaskDao
import com.fintech.denispok.fintechproject.db.dao.UserDao

@Database(entities = [Lecture::class, Task::class, Student::class, User::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun lectureDao(): LectureDao

    abstract fun taskDao(): TaskDao

    abstract fun studentDao(): StudentDao

    abstract fun userDao(): UserDao

}
