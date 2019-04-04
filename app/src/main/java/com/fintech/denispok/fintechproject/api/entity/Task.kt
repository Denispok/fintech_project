package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Task(
    @PrimaryKey @SerializedName("id") val id: Int,
    @Embedded @SerializedName("task") val taskDescription: TaskDescription,
    @SerializedName("status") val status: String,
    @SerializedName("mark") val mark: String
) {
    var lectureId: Int? = null
}
