package com.fintech.denispok.fintechproject.auth

import android.app.Activity
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.fintech.denispok.fintechproject.api.entity.User
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference
import java.util.regex.Pattern

class AuthCallback(activity: AppCompatActivity, sharedPreferences: SharedPreferences, val pattern: Pattern) :
    Callback<User> {

    private val activity = WeakReference(activity)
    private val sharedPreferences = WeakReference(sharedPreferences)

    override fun onFailure(call: Call<User>, t: Throwable) {
        Log.e("FAIL", t.message)
        showToast("Ошибка подключения")
    }

    override fun onResponse(call: Call<User>, response: Response<User>) {

        if (response.isSuccessful) {
            val preferences = sharedPreferences.get()
            val matcher = pattern.matcher(response.headers()["Set-Cookie"])

            if (matcher.find()) {
                val token = matcher.group()
                preferences?.edit()?.putString("token", token)?.apply()
                activity.get()?.apply {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            } else {
                showToast("Ошибка Авторизации")
            }
        } else if (response.code() == 403) {
            val errorResponse = JSONObject(response.errorBody()?.string()).getString("detail")
            showToast(errorResponse)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity.get(), message, Toast.LENGTH_LONG).show()
    }
}
