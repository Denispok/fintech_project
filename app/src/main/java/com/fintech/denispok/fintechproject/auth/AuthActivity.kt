package com.fintech.denispok.fintechproject.auth

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.AuthRequestBody
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import kotlinx.android.synthetic.main.activity_auth.*
import java.util.regex.Pattern

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.title = "Вход"

        val sharedPreferences = getSharedPreferences("cache", Context.MODE_PRIVATE)
        val cookiePattern = Pattern.compile("anygen=[^;]+")

        auth_loginButton.setOnClickListener {
            val retrofit = RetrofitProvider.getInstance()
            val apiService = retrofit.create(ApiService::class.java)
            val authCall = apiService.auth(
                AuthRequestBody(
                    auth_textInputLayout_email?.editText?.text.toString(),
                    auth_textInputLayout_password?.editText?.text.toString()
                )
            )

            authCall.enqueue(AuthCallback(this, sharedPreferences, cookiePattern))
        }
    }
}
