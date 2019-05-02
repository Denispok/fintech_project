package com.fintech.denispok.fintechproject.ui.auth

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        App.applicationComponent.inject(this)

        val authViewModel = ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java)

        authViewModel.getState().observe(this, Observer {
            when (it) {
                AuthViewModel.State.DEFAULT -> {
                    auth_editText_email.isEnabled = true
                    auth_editText_password.isEnabled = true
                    auth_loginButton.isEnabled = true
                }
                AuthViewModel.State.CONNECTING -> {
                    auth_editText_email.isEnabled = false
                    auth_editText_password.isEnabled = false
                    auth_loginButton.isEnabled = false
                }
                AuthViewModel.State.SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        })


        auth_loginButton.setOnClickListener {
            authViewModel.auth(
                    auth_editText_email.text.toString(),
                    auth_editText_password.text.toString(),
                    AuthCallback(this))
        }
    }
}
