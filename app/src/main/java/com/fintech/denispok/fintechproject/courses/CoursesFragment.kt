package com.fintech.denispok.fintechproject.courses

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.contacts.ContactsActivity
import com.fintech.denispok.fintechproject.customviews.BadgeView
import com.fintech.denispok.fintechproject.lectures.LecturesActivity
import java.lang.ref.WeakReference

class CoursesFragment : Fragment() {

    companion object {
        const val RANDOM_POINTS_CODE = 1
    }

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var badgesLayout: LinearLayout
    lateinit var handler: Handler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_courses, container, false)
        badgesLayout = view.findViewById(R.id.badges_layout)

        view.findViewById<ConstraintLayout>(R.id.progress_details).setOnClickListener {
            startActivity(Intent(context, ContactsActivity::class.java))
        }

        view.findViewById<ConstraintLayout>(R.id.rating_details).setOnClickListener {
            startActivity(Intent(context, LecturesActivity::class.java))
        }

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)

        swipeRefreshLayout.setOnRefreshListener {
            RandomPointsThread(this.handler, badgesLayout.childCount).start()
        }

        val swipeRefreshLayoutReference = WeakReference(swipeRefreshLayout)
        val badgesLayoutReference = WeakReference(badgesLayout)

        handler = Handler(Handler.Callback {
            if (it.what == RANDOM_POINTS_CODE) {
                val randomPoints = it.obj as Array<Int>
                for (i in 0 until randomPoints.size) {
                    val child = badgesLayoutReference.get()?.getChildAt(i)
                    if (child is BadgeView) {
                        child.count = randomPoints[i]
                    }
                }
                swipeRefreshLayoutReference.get()?.isRefreshing = false
                return@Callback true
            }
            false
        })
        return view
    }
}