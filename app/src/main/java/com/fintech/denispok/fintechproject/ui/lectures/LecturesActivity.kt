package com.fintech.denispok.fintechproject.ui.lectures

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.ui.lectures.tasks.TaskActivity
import com.fintech.denispok.fintechproject.ui.lectures.tasks.TaskActivity.Companion.EXTRA_LECTURE_KEY
import com.fintech.denispok.fintechproject.utilities.InjectorUtils

class LecturesActivity : AppCompatActivity() {

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: LecturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectures)

        swipeRefreshLayout = findViewById(R.id.lectures_swipeRefreshLayout)
        recyclerView = findViewById(R.id.lectures_recyclerView)

        val lecturesViewModelFactory = InjectorUtils.provideProfileViewModelFactory(applicationContext)
        val lecturesViewModel = ViewModelProvider(this, lecturesViewModelFactory).get(LecturesViewModel::class.java)

        swipeRefreshLayout.setOnRefreshListener {
            lecturesViewModel.updateLectures()
        }

        val layoutManager = LinearLayoutManager(this)
        recyclerAdapter = LecturesAdapter(listOf(), object : LecturesAdapter.OnLectureClickListener {

            override fun onLectureClick(lecture: Lecture) {
                val intent = Intent(this@LecturesActivity, TaskActivity::class.java)
                intent.putExtra(EXTRA_LECTURE_KEY, lecture.id)
                startActivity(intent)
            }
        })
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        lecturesViewModel.getLectures().observe(this, Observer {
            swipeRefreshLayout.isRefreshing = false
            if (it != null) {
                recyclerAdapter.lectures = it
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }
}
