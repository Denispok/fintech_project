package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Lecture(
    @PrimaryKey @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @Ignore @SerializedName("tasks") var tasks: List<Task> = emptyList()
) {

    constructor() : this(0, "")
}
