package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class TaskDescription(
    @ColumnInfo(name = "descriptionId") @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("task_type") val taskType: String,
    @SerializedName("max_score") val maxScore: String,
    @SerializedName("deadline_date") val deadlineDate: String? = null,
    @SerializedName("short_name") val shortName: String? = null
)
