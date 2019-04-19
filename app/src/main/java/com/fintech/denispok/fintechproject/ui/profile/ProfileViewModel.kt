package com.fintech.denispok.fintechproject.ui.profile

import android.arch.lifecycle.ViewModel
import com.fintech.denispok.fintechproject.repository.Repository
import com.fintech.denispok.fintechproject.repository.ResponseCallback

class ProfileViewModel(private val repository: Repository) : ViewModel() {

    fun getUser(callback: ResponseCallback? = null) = repository.getUser(callback)

    fun updateUserCache(callback: ResponseCallback? = null) = repository.updateUserCache(callback)

}
