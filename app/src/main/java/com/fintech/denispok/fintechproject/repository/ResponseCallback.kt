package com.fintech.denispok.fintechproject.repository

import retrofit2.Response

interface ResponseCallback<T> {

    fun onFailure(t: Throwable)

    fun onResponse(response: Response<T>)

}
