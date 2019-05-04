package com.fintech.denispok.fintechproject.api

import com.fintech.denispok.fintechproject.api.entity.Course
import com.fintech.denispok.fintechproject.api.entity.Event
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.api.entity.User
import com.google.gson.annotations.SerializedName

data class EventsResponseBody(
    var active: List<Event>,
    val archive: List<Event>
)

data class UserResponseBody(
    val user: User? = null,
    val message: String?,
    val status: String
)

data class LecturesResponseBody(
    @SerializedName("homeworks") val lectures: List<Lecture>
)

data class ConnectionsResponseBody(
    val courses: List<Course>
)
