package com.fintech.denispok.fintechproject.profile

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.fintech.denispok.fintechproject.MainActivity
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider
import com.fintech.denispok.fintechproject.api.UserResponseBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), MainActivity.IOnTabSelected {

    companion object {
        const val LAST_NAME_KEY = "last_name"
        const val FIRST_NAME_KEY = "first_name"
        const val MIDDLE_NAME_KEY = "middle_name"
    }

    lateinit var profileImageView: ImageView
    lateinit var firstNameView: TextView
    lateinit var lastNameView: TextView
    lateinit var middleNameView: TextView

    override fun onTabSelected() {
        val preferences = context!!.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val retrofit = RetrofitProvider.getInstance()
        val apiService = retrofit.create(ApiService::class.java)
        val userCall = apiService.getUser(preferences.getString("token", "")!!)
        userCall.enqueue(object : Callback<UserResponseBody> {

            override fun onFailure(call: Call<UserResponseBody>, t: Throwable) {
                Log.e("FAIL", t.message)
                showToast("Ошибка подключения")
            }

            override fun onResponse(call: Call<UserResponseBody>, response: Response<UserResponseBody>) {

                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    if (responseBody.status == "Ok") {
                        responseBody.user?.apply {
                            firstNameView.text = this.first_name
                            lastNameView.text = this.last_name
                            middleNameView.text = this.middle_name
                            avatar?.apply { getProfileImage(apiService, this.drop(1)) }
                        }
                    } else {
                        showToast(responseBody.message ?: "Ошибка получения профиля")
                    }
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.findViewById<Button>(R.id.button_edit).setOnClickListener {
            fragmentManager!!.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_profile_wrapper, EditFragment())
                    .commit()
        }
        profileImageView = view.findViewById(R.id.profile_image)
        firstNameView = view.findViewById(R.id.first_name)
        lastNameView = view.findViewById(R.id.last_name)
        middleNameView = view.findViewById(R.id.middle_name)
        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun getProfileImage(apiService: ApiService, avatar: String) {
        apiService.getImage(avatar).enqueue(object : Callback<ResponseBody> {

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                showToast("Ошибка подключения при загрузке аватара")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    val bytes = responseBody.bytes()
                    val imageBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    profileImageView.setImageBitmap(imageBitmap)

                } else {
                    showToast("Ошибка получения аватара профиля")
                }
            }
        })
    }
}
