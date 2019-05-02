package com.fintech.denispok.fintechproject.ui.auth

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.AuthRequestBody
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.repository.Repository
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class AuthViewModel(private val repository: Repository) : ViewModel() {

    enum class State {
        DEFAULT,
        CONNECTING,
        SUCCESS
    }

    private val cookiePattern = Pattern.compile("anygen=[^;]+")
    private val stateMutableLiveData = MutableLiveData<State>()

    init {
        stateMutableLiveData.value =
            if (repository.token.isEmpty()) State.DEFAULT
            else State.SUCCESS
    }

    fun auth(email: String, password: String, callback: AuthCallback) {

        if (!email.matches(Regex("""^\S+@\S+\.\S+$"""))) {
            callback.onFailure(App.applicationContext.getString(R.string.wrong_email))
            return
        }
        if (password.isEmpty()) {
            callback.onFailure(App.applicationContext.getString(R.string.wrong_password))
            return
        }

        stateMutableLiveData.postValue(State.CONNECTING)

        repository.authCall(AuthRequestBody(email, password)).enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("FAIL", t.message)
                stateMutableLiveData.postValue(State.DEFAULT)
                callback.onFailure(t.message)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {

                    val matcher = cookiePattern.matcher(response.headers()["Set-Cookie"])
                    if (matcher.find()) {
                        val token = matcher.group()
                        repository.token = token
                        stateMutableLiveData.postValue(State.SUCCESS)
                        callback.onSuccess()
                    } else {
                        stateMutableLiveData.postValue(State.DEFAULT)
                        callback.onFailure("Ошибка Авторизации")
                    }

                } else if (response.code() == 403) {
                    val errorResponse = JSONObject(response.errorBody()?.string()).getString("detail")
                    stateMutableLiveData.postValue(State.DEFAULT)
                    callback.onFailure(errorResponse)
                }
            }
        })
    }

    fun getState(): LiveData<State> = stateMutableLiveData
}
