package com.fintech.denispok.fintechproject.profile

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.fintech.denispok.fintechproject.MainActivity
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.ApiService
import com.fintech.denispok.fintechproject.api.RetrofitProvider

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

    lateinit var apiService: ApiService

    override fun onTabSelected() {
        val preferences = context!!.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val retrofit = RetrofitProvider.getInstance()
        apiService = retrofit.create(ApiService::class.java)
        val userCall = apiService.getUser(preferences.getString("token", "")!!)
        userCall.enqueue(ProfileUserCallback(this))
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
}
