package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import javax.inject.Inject

class ProfileFragment : Fragment() {

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var profileImageView: ImageView
    private lateinit var firstNameView: TextView
    private lateinit var lastNameView: TextView
    private lateinit var middleNameView: TextView

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.instance.applicationComponent.inject(this)
        profileViewModel = ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        swipeRefreshLayout = view.findViewById(R.id.profile_swipeRefreshLayout)
        profileImageView = view.findViewById(R.id.profile_image)
        firstNameView = view.findViewById(R.id.first_name)
        lastNameView = view.findViewById(R.id.last_name)
        middleNameView = view.findViewById(R.id.middle_name)

        swipeRefreshLayout.setOnRefreshListener {
            profileViewModel.updateUserCache(ProfileResponseCallback(this))
        }

        profileViewModel.getUser(ProfileResponseCallback(this)).observe(this, Observer { user ->
            swipeRefreshLayout.isRefreshing = false
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
