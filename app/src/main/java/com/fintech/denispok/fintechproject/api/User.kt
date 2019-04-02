package com.fintech.denispok.fintechproject.api

import com.google.gson.annotations.SerializedName

data class User(
    val birthday: String? = null,
    val email: String? = null,
    @SerializedName("first_name") val firstName: String? = null,
    @SerializedName("last_name") val lastName: String? = null,
    @SerializedName("middle_name") val middleName: String? = null,
    @SerializedName("phone_mobile") val phoneMobile: String? = null,
    @SerializedName("t_shirt_size") val tShirtSize: String? = null,
    @SerializedName("is_client") val isClient: Boolean? = null,
    @SerializedName("skype_login") val skypeLogin: String? = null,
    val description: String? = null,
    val region: String? = null,
    val school: String? = null,
    @SerializedName("school_graduation") val schoolGraduation: String? = null,
    val university: String? = null,
    val faculty: String? = null,
    @SerializedName("university_graduation") val universityGraduation: Int? = null,
    val grade: String? = null,
    val department: String? = null,
    @SerializedName("current_work") val currentWork: String? = null,
    val resume: String? = null,
    val notifications: List<Any>? = null,
    val id: Int? = null,
    val admin: Boolean? = null,
    val avatar: String? = null
)
