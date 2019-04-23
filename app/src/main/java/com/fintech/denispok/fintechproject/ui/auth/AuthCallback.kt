package com.fintech.denispok.fintechproject.ui.auth

import android.widget.Toast
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import java.lang.ref.WeakReference

class AuthCallback(activity: AuthActivity) : ResponseCallback {

    private val activity = WeakReference(activity)

    override fun onFailure(error: String?) {
        showToast(error)
    }

    fun onSuccess() {
        showToast("You logged in!")
    }

    private fun showToast(message: String?) {
        if (message != null)
            activity.get()?.also { Toast.makeText(it, message, Toast.LENGTH_SHORT).show() }
    }
}
