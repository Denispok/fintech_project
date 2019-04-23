package com.fintech.denispok.fintechproject.ui.lectures

import android.arch.lifecycle.Lifecycle
import android.widget.Toast
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import java.lang.ref.WeakReference

class LecturesResponseCallback(lecturesActivity: LecturesActivity): ResponseCallback {
    private val lecturesActivity= WeakReference(lecturesActivity)

    override fun onFailure(error: String?) {
        lecturesActivity.get()?.apply {
            runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}