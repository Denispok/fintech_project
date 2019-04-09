package com.fintech.denispok.fintechproject.students

import android.widget.Toast
import com.fintech.denispok.fintechproject.repository.ResponseCallback
import com.google.gson.JsonArray
import retrofit2.Response
import java.lang.ref.WeakReference

class StudentsUpdateCallback(studentsActivity: StudentsActivity) : ResponseCallback<JsonArray> {

    private val studentsActivity = WeakReference(studentsActivity)

    override fun onFailure(t: Throwable) {
        studentsActivity.get()?.apply {
            runOnUiThread {
                Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onResponse(response: Response<JsonArray>) {
        studentsActivity.get()?.apply {
            runOnUiThread {
                Toast.makeText(this, "We have got a response!", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}
