package com.fintech.denispok.fintechproject.ui.events

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.fintech.denispok.fintechproject.App
import com.fintech.denispok.fintechproject.R
import com.fintech.denispok.fintechproject.customviews.SwipeToRefreshLayout
import com.fintech.denispok.fintechproject.ui.MainActivity
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class EventsFragment : Fragment() {

    @Inject
    lateinit var eventsViewModelFactory: EventsViewModelFactory
    private lateinit var eventsViewModel: EventsViewModel
    private var activeEventsDisposable: Disposable? = null

    private lateinit var allEventsView: TextView
    private lateinit var swipeToRefreshLayout: SwipeToRefreshLayout
    private lateinit var activeEventsRecyclerView: RecyclerView
    private lateinit var activeEventsRecyclerAdapter: ActiveEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.applicationComponent.inject(this)
        eventsViewModel = ViewModelProvider(this, eventsViewModelFactory).get(EventsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        view.apply {
            allEventsView = findViewById(R.id.events_active_all)
            swipeToRefreshLayout = findViewById(R.id.events_swipeRefreshLayout)
            activeEventsRecyclerView = findViewById(R.id.events_active_recycler)
        }

        activeEventsRecyclerAdapter = ActiveEventsAdapter()
        activeEventsRecyclerView.adapter = activeEventsRecyclerAdapter
        activeEventsRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        activeEventsDisposable = eventsViewModel.getActiveEvents().subscribe({
            allEventsView.text = "Все ${it.size}"
            activeEventsRecyclerAdapter.events = it
        }, {
            if (isTabSelected()) Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        })

        swipeToRefreshLayout.setOnRefreshListener {
            activeEventsDisposable?.dispose()
            activeEventsDisposable = eventsViewModel.getActiveEvents().subscribe({
                allEventsView.text = "Все ${it.size}"
                activeEventsRecyclerAdapter.events = it
            }, {
                if (isTabSelected()) Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                swipeToRefreshLayout.isRefreshing = false
            }, {
                swipeToRefreshLayout.isRefreshing = false
            })
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        activeEventsDisposable?.dispose()
        activeEventsDisposable = null
    }

    private fun isTabSelected(): Boolean = (activity!! as MainActivity).currentTab == MainActivity.EVENTS_TAB

}
