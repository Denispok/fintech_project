package com.fintech.denispok.fintechproject.lectures

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.api.entity.Lecture
import com.fintech.denispok.fintechproject.lectures.tasks.TaskActivity
import com.fintech.denispok.fintechproject.lectures.tasks.TaskActivity.Companion.EXTRA_LECTURE_KEY
import com.fintech.denispok.fintechproject.utilities.InjectorUtils

class LecturesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: LecturesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lectures)

        val layoutManager = LinearLayoutManager(this)
        recyclerAdapter = LecturesAdapter(listOf(), object : LecturesAdapter.OnLectureClickListener {

            override fun onLectureClick(lecture: Lecture) {
                val intent = Intent(this@LecturesActivity, TaskActivity::class.java)
                intent.putExtra(EXTRA_LECTURE_KEY, lecture.id)
                startActivity(intent)
            }
        })
        recyclerView = findViewById(R.id.lectures_recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        val lecturesViewModelFactory = InjectorUtils.provideProfileViewModelFactory(applicationContext)
        val lecturesViewModel = ViewModelProvider(this, lecturesViewModelFactory).get(LecturesViewModel::class.java)
        lecturesViewModel.getLectures().observe(this, Observer {
            if (it != null) {
                recyclerAdapter.lectures = it
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }
}
