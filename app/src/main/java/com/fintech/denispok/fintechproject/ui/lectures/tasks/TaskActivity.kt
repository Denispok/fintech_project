package com.fintech.denispok.fintechproject.ui.lectures.tasks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModel
import com.fintech.denispok.fintechproject.ui.lectures.LecturesViewModelFactory
import javax.inject.Inject

class TaskActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LECTURE_TITLE = "lecture_title"
        const val EXTRA_LECTURE_ID = "lecture_id"
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: TaskAdapter

    @Inject
    lateinit var lecturesViewModelFactory: LecturesViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        val lectureTitle = intent.getStringExtra(EXTRA_LECTURE_TITLE)
        val lectureId = intent.getIntExtra(EXTRA_LECTURE_ID, 0)

        supportActionBar?.title = lectureTitle

        App.applicationComponent.inject(this)
        val lecturesViewModel = ViewModelProvider(this, lecturesViewModelFactory).get(LecturesViewModel::class.java)

        val layoutManager = LinearLayoutManager(this)
        recyclerAdapter = TaskAdapter(listOf())
        recyclerView = findViewById(R.id.task_recyclerView)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = recyclerAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        lecturesViewModel.getTasks(lectureId).observe(this, Observer {
            if (it != null) {
                recyclerAdapter.tasks = it
                recyclerAdapter.notifyDataSetChanged()
            }
        })
    }
}
