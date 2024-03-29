package com.fintech.denispok.fintechproject.ui.students

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class StudentsActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerLayoutManager: GridLayoutManager
    private lateinit var recyclerAdapter: StudentsAdapter

    @Inject
    lateinit var studentsViewModelFactory: StudentsViewModelFactory
    private var studentsDisposable: Disposable? = null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.students_action_bar, menu)

        val myActionMenuItem = menu.findItem(R.id.students_action_bar_search)
        val searchView = myActionMenuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                recyclerAdapter.filter = query
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                recyclerAdapter.filter = s
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.students_action_bar_sort_alphabet) {
            recyclerAdapter.sortType = StudentsAdapter.SORT_TYPE_ALPHABET
            return true
        } else if (item.itemId == R.id.students_action_bar_sort_points) {
            recyclerAdapter.sortType = StudentsAdapter.SORT_TYPE_POINTS
            return true
        } else if (item.itemId == R.id.students_action_bar_change_view) {
            changeView()
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students)
        supportActionBar?.title = "Успеваемость"

        App.applicationComponent.inject(this)

        val studentsViewModel = ViewModelProvider(this, studentsViewModelFactory).get(StudentsViewModel::class.java)

        swipeRefreshLayout = findViewById(R.id.activity_students)
        recyclerView = findViewById(R.id.students_recycler_view)

        recyclerLayoutManager = GridLayoutManager(this, 1)
        recyclerAdapter = StudentsAdapter(listOf())
        recyclerView.layoutManager = recyclerLayoutManager
        recyclerView.adapter = recyclerAdapter

        studentsDisposable = studentsViewModel.getStudents().subscribe({
            recyclerAdapter.students = it
        }, {
            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        })

        swipeRefreshLayout.setOnRefreshListener {
            studentsViewModel.getStudents().subscribe({
                recyclerAdapter.students = it
            }, {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                swipeRefreshLayout.isRefreshing = false
            }, {
                swipeRefreshLayout.isRefreshing = false
            })
        }
    }

    override fun onPause() {
        super.onPause()
        studentsDisposable?.dispose()
        studentsDisposable = null
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
