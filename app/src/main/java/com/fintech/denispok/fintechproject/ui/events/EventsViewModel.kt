package com.fintech.denispok.fintechproject.ui.events

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.api.entity.Event
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventsViewModel(private val repository: Repository) : ViewModel() {

    fun getActiveEvents(): Observable<List<Event>> =
        repository.getActiveEvents()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}
