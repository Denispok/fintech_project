package com.fintech.denispok.fintechproject.ui.lectures.tasks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModel
import com.fintech.denispok.fintechproject.utilities.InjectorUtils

class TaskActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LECTURE_KEY = "lecture_id"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val layoutManager = LinearLayoutManager(this)
        recyclerAdapter = TaskAdapter(listOf())
        recyclerView = findViewById(R.id.task_recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        val lectureId = intent.getIntExtra(EXTRA_LECTURE_KEY, 0)
        val lecturesViewModelFactory = InjectorUtils.provideProfileViewModelFactory(applicationContext)
        val lecturesViewModel = ViewModelProvider(this, lecturesViewModelFactory).get(LecturesViewModel::class.java)
        lecturesViewModel.getTasks(lectureId).observe(this, Observer {
            if (it != null) {
                recyclerAdapter.tasks = it
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }
}