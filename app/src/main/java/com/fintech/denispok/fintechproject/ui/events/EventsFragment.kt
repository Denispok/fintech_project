package com.fintech.denispok.fintechproject.ui.events

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventsFragment: Fragment() {

    @Inject
    lateinit var repository: Repository

    private lateinit var allEventsView: TextView

    private lateinit var activeEventsRecyclerView: RecyclerView
    private lateinit var activeEventsRecyclerAdapter: ActiveEventsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        view.apply {
            allEventsView = findViewById(R.id.events_active_all)
            activeEventsRecyclerView = findViewById(R.id.events_active_recycler)
        }

        activeEventsRecyclerAdapter = ActiveEventsAdapter()
        activeEventsRecyclerView.adapter = activeEventsRecyclerAdapter
        activeEventsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        App.applicationComponent.inject(this)
        repository.getActiveEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                allEventsView.text = "Все ${it.size}"
                activeEventsRecyclerAdapter.events = it
            },
            {
                val mes = it.message
            })
        return view
    }
}
