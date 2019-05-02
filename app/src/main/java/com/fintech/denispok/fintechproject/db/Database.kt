package com.fintech.denispok.fintechproject.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fintech.denispok.fintechproject.api.entity.*
import com.fintech.denispok.fintechproject.db.dao.*

@Database(entities = [Lecture::class, Task::class, Student::class, User::class, Event::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun lectureDao(): LectureDao

    abstract fun taskDao(): TaskDao

    abstract fun studentDao(): StudentDao

    abstract fun userDao(): UserDao

    abstract fun eventDao(): EventDao

}
