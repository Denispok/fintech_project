package com.fintech.denispok.fintechproject.repository.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.fintech.denispok.fintechproject.api.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Insert
    fun insertUser(user: User)

    @Query("DELETE FROM user")
    fun deleteAllUsers()

}
