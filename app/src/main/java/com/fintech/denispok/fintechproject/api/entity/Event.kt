package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Event(
    @PrimaryKey var id: Int = 0,
    var title: String,
    @SerializedName("date_start") var dateStart: String,
    @SerializedName("date_end") var dateEnd: String,
    @Embedded @SerializedName("event_type") var eventType: EventType?,
    @SerializedName("custom_date") var customDate: String,
    var url: String,
    @SerializedName("url_external") var isUrlExternal: String,
    var description: String,
    var isPassed: Boolean = false
)

data class EventType(
    var name: String,
    var color: String
)
