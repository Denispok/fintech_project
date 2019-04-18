package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.Lifecycle
import android.widget.Toast
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import java.lang.ref.WeakReference

class ProfileResponseCallback(profileFragment: ProfileFragment): ResponseCallback {

    private val profileFragment = WeakReference(profileFragment)

    override fun onFailure(error: String?) {
        profileFragment.get()?.apply {
            activity?.runOnUiThread {
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Toast.makeText(activity, error ?: "Ошибка загрузки профиля", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
