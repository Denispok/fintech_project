package com.fintech.denispok.fintechproject.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.Lecture

@Dao
interface LectureDao {

    @Query("SELECT * FROM lecture")
    fun getLectures(): List<Lecture>

    @Insert
    fun insertLectures(lectures: List<Lecture>)

    @Query("DELETE FROM lecture")
    fun deleteAllLectures()

}
