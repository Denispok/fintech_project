package com.fintech.denispok.fintechproject

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.AuthRequestBody
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.api.User
import kotlinx.android.synthetic.main.activity_auth.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.title = "Вход"

        auth_loginButton.setOnClickListener {
            val retrofit = RetrofitProvider.getInstance()
            val apiService = retrofit.create(ApiService::class.java)
            val authCall = apiService.auth(
                AuthRequestBody(
                    auth_textInputLayout_email?.editText?.text.toString(),
                    auth_textInputLayout_password?.editText?.text.toString()
                )
            )

            authCall.enqueue(object : Callback<User> {

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e("FAIL", t.message)
                    showToast("Ошибка подключения")
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {

                    if (response.isSuccessful) {
                        val preferences = getSharedPreferences("auth", Context.MODE_PRIVATE)
                        val pattern = Pattern.compile("anygen=[^;]+")
                        val matcher = pattern.matcher(response.headers()["Set-Cookie"])

                        if (matcher.find()) {
                            val token = matcher.group()
                            preferences.edit().apply {
                                putString("token", token)
                                apply()
                            }
                            setResult(Activity.RESULT_OK)
                            finish()
                        } else {
                            showToast("Ошибка Авторизации")
                        }
                    } else if (response.code() == 403) {
                        val errorResponse = JSONObject(response.errorBody()?.string()).getString("detail")
                        showToast(errorResponse)
                    }
                }
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
