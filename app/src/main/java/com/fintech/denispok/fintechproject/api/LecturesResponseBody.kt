package com.fintech.denispok.fintechproject.api

import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.google.gson.annotations.SerializedName

data class LecturesResponseBody(@SerializedName("homeworks") val lectures: List<Lecture>)
