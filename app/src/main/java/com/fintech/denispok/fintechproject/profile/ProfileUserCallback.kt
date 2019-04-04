package com.fintech.denispok.fintechproject.profile

import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.api.UserResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ref.WeakReference

class ProfileUserCallback(profileFragment: ProfileFragment) : Callback<UserResponseBody> {

    private val profileFragment = WeakReference(profileFragment)

    override fun onFailure(call: Call<UserResponseBody>, t: Throwable) {
        Log.e("FAIL", t.message)
        showToast("Ошибка подключения")
    }

    override fun onResponse(call: Call<UserResponseBody>, response: Response<UserResponseBody>) {

        if (response.isSuccessful) {
            val responseBody = response.body()!!
            if (responseBody.status == "Ok") {

                responseBody.user?.also { user: User ->

                    profileFragment.get()?.apply {
                        firstNameView.text = user.firstName
                        lastNameView.text = user.lastName
                        middleNameView.text = user.middleName

                        user.avatar?.also {
                            Glide.with(this)
                                .load("https://fintech.tinkoff.ru/" + it.drop(1))
                                .into(profileImageView)
                        }

                    }
                }
            } else {
                showToast(responseBody.message ?: "Ошибка получения профиля")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(profileFragment.get()?.context, message, Toast.LENGTH_LONG).show()
    }
}
