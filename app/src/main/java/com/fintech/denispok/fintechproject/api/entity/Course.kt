package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Course(
    val title: String,
    @PrimaryKey val url: String
)
