package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getUser() = repository.getUser()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}
