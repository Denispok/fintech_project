package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.profile.edit.EditFragment
import com.fintech.denispok.fintechproject.utilities.InjectorUtils

class ProfileFragment : Fragment() {

    companion object {
        const val LAST_NAME_KEY = "last_name"
        const val FIRST_NAME_KEY = "first_name"
        const val MIDDLE_NAME_KEY = "middle_name"
    }

    private lateinit var profileImageView: ImageView
    private lateinit var firstNameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var middleNameView: TextView

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val profileViewModelFactory = InjectorUtils.provideProfileViewModelFactory(activity!!.applicationContext)
        profileViewModel = ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
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

        profileViewModel.getUser(ProfileResponseCallback(this)).observe(this, Observer { user ->
            user?.apply {
                firstNameView.text = firstName
                lastNameView.text = lastName
                middleNameView.text = middleName
                avatar?.also { avatar ->
                    Glide.with(this@ProfileFragment)
                            .load("https://fintech.tinkoff.ru/" + avatar.drop(1))
                            .into(profileImageView)
                }
            }
        })

        return view
    }
}
