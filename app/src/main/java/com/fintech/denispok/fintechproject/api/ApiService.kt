package com.fintech.denispok.fintechproject.api

import com.fintech.denispok.fintechproject.api.entity.User
import com.google.gson.JsonArray
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers(
            "Content-Type: application/json",
            "Host: fintech.tinkoff.ru"
    )
    @POST("api/signin")
    fun auth(@Body authRequestBody: AuthRequestBody): Call<User>

    @Headers("Host: fintech.tinkoff.ru")
    @GET("api/user")
    fun getUser(@Header("Cookie") token: String): Call<UserResponseBody>

    @Headers("Host: fintech.tinkoff.ru")
    @GET("api/course/android_spring_2019/homeworks")
    fun getLectures(@Header("Cookie") token: String): Call<LecturesResponseBody>

    @Headers("Host: fintech.tinkoff.ru")
    @GET("api/course/android_spring_2019/grades")
    fun getGrades(@Header("Cookie") token: String): Call<JsonArray>
}
