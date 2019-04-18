package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import kotlin.random.Random

@Entity
data class Student(
        @PrimaryKey val id: Long,
        val name: String,
        val mark: Double,
        val color: Int = Random.nextInt(16777216) - 16777216
)
