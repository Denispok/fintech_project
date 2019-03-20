package com.fintech.denispok.fintechproject

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class ProfileFragment : Fragment() {

    companion object {
        const val LAST_NAME_KEY = "last_name"
        const val FIRST_NAME_KEY = "first_name"
        const val MIDDLE_NAME_KEY = "middle_name"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.findViewById<Button>(R.id.button_edit).setOnClickListener {
            fragmentManager!!.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_profile_wrapper, EditFragment())
                .commit()
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        val profile = activity!!.getSharedPreferences("profile", Context.MODE_PRIVATE)
        view!!.findViewById<TextView>(R.id.last_name).text = profile.getString(LAST_NAME_KEY, "")
        view!!.findViewById<TextView>(R.id.first_name).text = profile.getString(FIRST_NAME_KEY, "")
        view!!.findViewById<TextView>(R.id.middle_name).text = profile.getString(MIDDLE_NAME_KEY, "")
    }
}
