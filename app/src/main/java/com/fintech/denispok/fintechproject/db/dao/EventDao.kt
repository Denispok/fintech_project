package com.fintech.denispok.fintechproject.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.Event

@Dao
interface EventDao {

    @Insert
    fun insertEvents(events: List<Event>)

    @Query("SELECT * FROM event WHERE isPassed = 0")
    fun getActiveEvents(): List<Event>

    @Query("SELECT * FROM event WHERE isPassed = 1")
    fun getArchiveEvents(): List<Event>

    @Query("DELETE FROM event")
    fun deleteAllEvents()

}
