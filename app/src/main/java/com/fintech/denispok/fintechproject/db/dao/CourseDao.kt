package com.fintech.denispok.fintechproject.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.Course

@Dao
interface CourseDao {

    @Insert
    fun insertCourses(courses: List<Course>)

    @Query("SELECT * FROM course")
    fun getCourses(): List<Course>

    @Query("DELETE FROM course")
    fun deleteAllCourses()

}
