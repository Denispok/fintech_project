package com.fintech.denispok.fintechproject.repository

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.Student
import com.fintech.denispok.fintechproject.api.entity.Task
import com.fintech.denispok.fintechproject.repository.dao.LectureDao
import com.fintech.denispok.fintechproject.repository.dao.StudentDao
import com.fintech.denispok.fintechproject.repository.dao.TaskDao

@Database(entities = [Lecture::class, Task::class, Student::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun lectureDao(): LectureDao

    abstract fun taskDao(): TaskDao

    abstract fun studentDao(): StudentDao

}
