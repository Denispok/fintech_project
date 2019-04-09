package com.fintech.denispok.fintechproject.students

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.utilities.InjectorUtils

class StudentsActivity : AppCompatActivity() {

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerLayoutManager: GridLayoutManager
    private lateinit var recyclerAdapter: StudentsAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.contacts_action_bar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.contacts_action_bar_change_view) {
            changeView()
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)

        swipeRefreshLayout = findViewById(R.id.activity_students)
        recyclerView = findViewById(R.id.students_recycler_view)

        val studentsViewModelFactory = InjectorUtils.provideStudentsViewModelFactory(applicationContext)
        val studentsViewModel = ViewModelProvider(this, studentsViewModelFactory).get(StudentsViewModel::class.java)

        recyclerLayoutManager = GridLayoutManager(this, 1)
        recyclerAdapter = StudentsAdapter(listOf())
        recyclerView.layoutManager = recyclerLayoutManager
        recyclerView.adapter = recyclerAdapter

        studentsViewModel.getStudents().observe(this, Observer {
            if (it != null) {
                recyclerAdapter.students = it
                recyclerAdapter.notifyItemRangeChanged(0, recyclerAdapter.students.size)
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            studentsViewModel.updateStudents(StudentsUpdateCallback(this))
        }
    }

    private fun changeView() {
        when (recyclerAdapter.viewType) {
            StudentsAdapter.LINEAR_VIEW_TYPE -> {
                recyclerLayoutManager.spanCount = 3
                recyclerAdapter.viewType = StudentsAdapter.GRID_VIEW_TYPE
            }
            StudentsAdapter.GRID_VIEW_TYPE -> {
                recyclerLayoutManager.spanCount = 1
                recyclerAdapter.viewType = StudentsAdapter.LINEAR_VIEW_TYPE
            }
        }
    }
}
