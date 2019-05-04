package com.fintech.denispok.fintechproject.ui.courses

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.customviews.BadgeView
import com.fintech.denispok.fintechproject.ui.lectures.LecturesActivity
import com.fintech.denispok.fintechproject.ui.students.StudentsActivity
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference
import javax.inject.Inject

class CoursesFragment : Fragment() {

    companion object {
        const val RANDOM_POINTS_CODE = 1
    }

    @Inject
    lateinit var coursesViewModelFactory: CoursesViewModelFactory
    private lateinit var coursesViewModel: CoursesViewModel
    private var coursesDisposable: Disposable? = null

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var badgesLayout: LinearLayout
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        coursesViewModel = ViewModelProvider(this, coursesViewModelFactory).get(CoursesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_courses, container, false)

        view.apply {
            badgesLayout = findViewById(R.id.badges_layout)
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        }

        view.findViewById<ConstraintLayout>(R.id.progress_details).setOnClickListener {
            startActivity(Intent(context, StudentsActivity::class.java))
        }

        view.findViewById<ConstraintLayout>(R.id.rating_details).setOnClickListener {
            startActivity(Intent(context, LecturesActivity::class.java))
        }

        coursesDisposable = coursesViewModel.getCourses().subscribe({
            val course = it[0]
            (activity as AppCompatActivity).supportActionBar?.title = course.title
        }, {
            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })

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

    override fun onPause() {
        super.onPause()
        coursesDisposable?.dispose()
        coursesDisposable = null
    }
}
