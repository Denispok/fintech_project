package com.fintech.denispok.fintechproject.ui.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.MainActivity
import com.fintech.denispok.fintechproject.utilities.InjectorUtils
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        supportActionBar?.title = "Вход"

        val authViewModelFactory = InjectorUtils.provideAuthViewModelFactory(applicationContext)
        val authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        authViewModel.getState().observe(this, Observer {
            when (it) {
                AuthViewModel.State.DEFAULT -> {
                    // TODO
                }
                AuthViewModel.State.CONNECTING -> {
                    // TODO
                }
                AuthViewModel.State.SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        })


        auth_loginButton.setOnClickListener {
            authViewModel.auth(
                    auth_textInputLayout_email?.editText?.text.toString(),
                    auth_textInputLayout_password?.editText?.text.toString(),
                    AuthCallback(this))
        }
    }
}
