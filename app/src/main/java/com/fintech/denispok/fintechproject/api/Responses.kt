package com.fintech.denispok.fintechproject.api

import com.fintech.denispok.fintechproject.api.entity.Event

data class EventsResponseBody(
    var active: List<Event>,
    val archive: List<Event>
)
