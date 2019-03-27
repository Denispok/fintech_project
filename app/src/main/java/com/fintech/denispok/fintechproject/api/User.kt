package com.fintech.denispok.fintechproject.api

data class User(
    val birthday: String? = null,
    val email: String? = null,
    val first_name: String? = null,
    val last_name: String? = null,
    val middle_name: String? = null,
    val phone_mobile: String? = null,
    val t_shirt_size: String? = null,
    val is_client: Boolean? = null,
    val skype_login: String? = null,
    val description: String? = null,
    val region: String? = null,
    val school: String? = null,
    val school_graduation: String? = null,
    val university: String? = null,
    val faculty: String? = null,
    val university_graduation: Int? = null,
    val grade: String? = null,
    val department: String? = null,
    val current_work: String? = null,
    val resume: String? = null,
    val notifications: List<Any>? = null,
    val id: Int? = null,
    val admin: Boolean? = null,
    val avatar: String? = null
)
