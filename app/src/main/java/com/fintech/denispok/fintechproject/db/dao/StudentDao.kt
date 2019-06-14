package com.fintech.denispok.fintechproject.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.Student

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getStudents(): List<Student>

    @Query("SELECT * FROM student WHERE id = :id")
    fun getStudent(id: Long): Student

    @Insert
    fun insertStudents(students: List<Student>)

    @Query("DELETE FROM student")
    fun deleteAllStudents()
}
