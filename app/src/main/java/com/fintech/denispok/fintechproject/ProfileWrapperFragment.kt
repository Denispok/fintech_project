package com.fintech.denispok.fintechproject

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProfileWrapperFragment : Fragment(), MainActivity.IOnBackPressed {

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.fragment_profile_wrapper)
        if (fragment is MainActivity.IOnBackPressed) {
            return fragment.onBackPressed()
        }
        return false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_wrapper, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_profile_wrapper, ProfileFragment())
            .commit()
    }
}
