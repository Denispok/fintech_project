package com.fintech.denispok.fintechproject.api

import okhttp3.ResponseBody
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
    @GET("{avatar}")
    fun getImage(@Path("avatar") avatar: String): Call<ResponseBody>
}
