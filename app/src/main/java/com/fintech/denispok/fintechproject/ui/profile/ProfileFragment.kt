package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.RetrofitModule.Companion.BASE_URL
import com.fintech.denispok.fintechproject.api.entity.User
import com.fintech.denispok.fintechproject.ui.MainActivity
import io.reactivex.disposables.Disposable
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
    private var profileDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        profileViewModel = ViewModelProvider(this, profileViewModelFactory).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        swipeRefreshLayout = view.findViewById(R.id.profile_swipeRefreshLayout)
        profileImageView = view.findViewById(R.id.profile_image)
        firstNameView = view.findViewById(R.id.first_name)
        lastNameView = view.findViewById(R.id.last_name)
        middleNameView = view.findViewById(R.id.middle_name)

        val onNextUser: (User) -> Unit = { user ->
            user.apply {
                firstNameView.text = firstName
                lastNameView.text = lastName
                middleNameView.text = middleName
                avatar?.also { avatar ->
                    Glide.with(this@ProfileFragment)
                        .load(BASE_URL + avatar.drop(1))
                        .into(profileImageView)
                }
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            profileViewModel.getUser().subscribe(onNextUser, {
                if (isTabSelected()) Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }, {
                swipeRefreshLayout.isRefreshing = false
            })
        }

        profileDisposable = profileViewModel.getUser().subscribe(onNextUser, {
            if (isTabSelected()) Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
        })

        return view
    }

    override fun onPause() {
        super.onPause()
        profileDisposable?.dispose()
        profileDisposable = null
    }

    private fun isTabSelected(): Boolean = (activity!! as MainActivity).currentTab == MainActivity.PROFILE_TAB
}
