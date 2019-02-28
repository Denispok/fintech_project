package com.fintech.denispok.fintechproject

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.fintech.denispok.fintechproject.ProfileFragment.Companion.FIRST_NAME_KEY
import com.fintech.denispok.fintechproject.ProfileFragment.Companion.LAST_NAME_KEY
import com.fintech.denispok.fintechproject.ProfileFragment.Companion.MIDDLE_NAME_KEY

class EditFragment : Fragment(), MainActivity.IOnBackPressed {

    private lateinit var lastName: EditText
    private lateinit var firstName: EditText
    private lateinit var middleName: EditText
    private lateinit var profilePreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        profilePreferences = activity!!.getSharedPreferences("profile", Context.MODE_PRIVATE)

        lastName = view.findViewById(R.id.last_name)
        firstName = view.findViewById(R.id.first_name)
        middleName = view.findViewById(R.id.middle_name)

        lastName.setText(profilePreferences.getString(LAST_NAME_KEY, ""))
        firstName.setText(profilePreferences.getString(FIRST_NAME_KEY, ""))
        middleName.setText(profilePreferences.getString(MIDDLE_NAME_KEY, ""))

        view.findViewById<Button>(R.id.button_save).setOnClickListener {
            if (isProfileValid()) {
                profilePreferences.edit().apply {
                    putString(LAST_NAME_KEY, lastName.text.toString().trim())
                    putString(FIRST_NAME_KEY, firstName.text.toString().trim())
                    putString(MIDDLE_NAME_KEY, middleName.text.toString().trim())
                    commit()
                }
                fragmentManager!!.popBackStack()
            } else {
                Toast.makeText(context!!, R.string.invalid_profile, Toast.LENGTH_LONG).show()
            }
        }
        view.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            leaveFragment()
        }
        return view
    }

    private fun isProfileChanged(): Boolean {
        if (profilePreferences.getString(LAST_NAME_KEY, "") != lastName.text.toString().trim() ||
            profilePreferences.getString(FIRST_NAME_KEY, "") != firstName.text.toString().trim() ||
            profilePreferences.getString(MIDDLE_NAME_KEY, "") != middleName.text.toString().trim()
        )
            return true
        return false
    }

    private fun isProfileValid(): Boolean {
        if (!isFieldValid(lastName.text.toString()) ||
            !isFieldValid(firstName.text.toString()) ||
            !isFieldValid(middleName.text.toString())
        ) return false
        return true
    }

    private fun isFieldValid(field: String): Boolean {
        val trimmedField = field.trim()
        if (trimmedField.isEmpty()) return false
        if (trimmedField[0].isLowerCase()) return false
        return true
    }

    private fun leaveFragment() {
        if (isProfileChanged()) {
            val dialog = ConfirmDialogFragment.newInstance(object : ConfirmDialogFragment.FinishListener {
                override fun onClick() {
                    fragmentManager!!.popBackStack()
                }
            })
            dialog.show(fragmentManager, "confirm_dialog")
        } else {
            fragmentManager!!.popBackStack()
        }
    }

    override fun onBackPressed() {
        leaveFragment()
    }
}