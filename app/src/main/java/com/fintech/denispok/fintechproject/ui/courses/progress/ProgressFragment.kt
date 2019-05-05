package com.fintech.denispok.fintechproject.ui.courses.progress

import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.students.StudentsActivity
import com.fintech.denispok.fintechproject.ui.students.StudentsViewModel
import com.fintech.denispok.fintechproject.ui.students.StudentsViewModelFactory
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class ProgressFragment : Fragment() {

    @Inject
    lateinit var studentsViewModelFactory: StudentsViewModelFactory
    private lateinit var studentsViewModel: StudentsViewModel
    private var studentsDisposable: Disposable? = null

    private lateinit var badgesRecycler: RecyclerView
    private lateinit var badgesAdapter: BadgesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        studentsViewModel = ViewModelProvider(this, studentsViewModelFactory).get(StudentsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_progress, container, false)

        badgesRecycler = view.findViewById(R.id.progress_badges_recycler)
        view.findViewById<ConstraintLayout>(R.id.progress_details).setOnClickListener {
            startActivity(Intent(context, StudentsActivity::class.java))
        }

        badgesAdapter = BadgesAdapter()
        badgesRecycler.adapter = badgesAdapter
        badgesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        studentsDisposable = studentsViewModel.getTop10Students().subscribe {
            badgesAdapter.students = it
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        studentsDisposable?.dispose()
        studentsDisposable = null
    }

    fun updateStudents() {
        studentsDisposable?.dispose()
        studentsDisposable = studentsViewModel.getTop10Students().subscribe {
            badgesAdapter.students = it
        }
    }
}
