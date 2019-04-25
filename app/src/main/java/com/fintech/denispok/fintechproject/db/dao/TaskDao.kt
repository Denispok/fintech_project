package com.fintech.denispok.fintechproject.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task WHERE lectureId = :lectureId")
    fun getTasks(lectureId: Int): List<Task>

    @Insert
    fun insertTasks(tasks: List<Task>)

    @Query("DELETE FROM task")
    fun deleteAllTasks()

}
