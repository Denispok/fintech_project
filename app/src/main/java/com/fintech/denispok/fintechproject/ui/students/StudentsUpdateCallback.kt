package com.fintech.denispok.fintechproject.ui.students

import android.arch.lifecycle.Lifecycle
import android.widget.Toast
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import java.lang.ref.WeakReference

class StudentsUpdateCallback(studentsActivity: StudentsActivity) : ResponseCallback {

    private val studentsActivity = WeakReference(studentsActivity)

    override fun onFailure(error: String?) {
        studentsActivity.get()?.apply {
            runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onServerResponse() {
        studentsActivity.get()?.apply {
            runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Toast.makeText(this, "We have got a response!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onCacheResponse() {
        studentsActivity.get()?.apply {
            runOnUiThread {
                swipeRefreshLayout.isRefreshing = false
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    Toast.makeText(this, "We have got data from cache!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
