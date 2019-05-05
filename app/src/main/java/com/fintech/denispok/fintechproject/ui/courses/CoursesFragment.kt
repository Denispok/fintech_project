package com.fintech.denispok.fintechproject.ui.courses

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.MainActivity
import com.fintech.denispok.fintechproject.ui.courses.progress.ProgressFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class CoursesFragment : Fragment(), MainActivity.IOnTabSelected {

    @Inject
    lateinit var coursesViewModelFactory: CoursesViewModelFactory
    private lateinit var coursesViewModel: CoursesViewModel
    private var coursesDisposable: Disposable? = null

    private lateinit var progressFragment: ProgressFragment
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var title: String = "Мои курсы"

    override fun onTabSelected() {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        coursesViewModel = ViewModelProvider(this, coursesViewModelFactory).get(CoursesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_courses, container, false)

        view.apply {
            progressFragment = childFragmentManager.findFragmentById(R.id.fragment_progress) as ProgressFragment
            swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        }

        coursesDisposable = coursesViewModel.getCourses().subscribe({
            if (it.isNotEmpty()) {
                val course = it[0]
                title = course.title
                if (isTabSelected()) (activity as AppCompatActivity).supportActionBar?.title = title
            }
        }, {
            if (isTabSelected()) Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })


        swipeRefreshLayout.setOnRefreshListener {
            coursesDisposable?.dispose()
            coursesDisposable = coursesViewModel.getCourses().subscribe({
                if (it.isNotEmpty()) {
                    val course = it[0]
                    title = course.title
                    (activity as AppCompatActivity).supportActionBar?.title = title
                }
            }, {
                if (isTabSelected()) Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                swipeRefreshLayout.isRefreshing = false
            }, {
                swipeRefreshLayout.isRefreshing = false
            })
            progressFragment.updateStudents()
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        coursesDisposable?.dispose()
        coursesDisposable = null
    }

    private fun isTabSelected(): Boolean = (activity!! as MainActivity).currentTab == MainActivity.COURSES_TAB

}
