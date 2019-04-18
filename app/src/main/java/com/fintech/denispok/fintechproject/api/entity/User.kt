package com.fintech.denispok.fintechproject.api.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
        var birthday: String? = null,
        var email: String? = null,
        @SerializedName("first_name") var firstName: String? = null,
        @SerializedName("last_name") var lastName: String? = null,
        @SerializedName("middle_name") var middleName: String? = null,
        @SerializedName("phone_mobile") var phoneMobile: String? = null,
        @SerializedName("t_shirt_size") var tShirtSize: String? = null,
        @SerializedName("is_client") var isClient: Boolean? = null,
        @SerializedName("skype_login") var skypeLogin: String? = null,
        var description: String? = null,
        var region: String? = null,
        var school: String? = null,
        @SerializedName("school_graduation") var schoolGraduation: String? = null,
        var university: String? = null,
        var faculty: String? = null,
        @SerializedName("university_graduation") var universityGraduation: Int? = null,
        var grade: String? = null,
        var department: String? = null,
        @SerializedName("current_work") var currentWork: String? = null,
        var resume: String? = null,
        @PrimaryKey var id: Int? = null,
        var admin: Boolean? = null,
        var avatar: String? = null
)
