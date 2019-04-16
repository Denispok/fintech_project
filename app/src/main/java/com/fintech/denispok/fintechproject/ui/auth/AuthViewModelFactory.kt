package com.fintech.denispok.fintechproject.ui.auth

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fintech.denispok.fintechproject.repository.Repository

class AuthViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository) as T
    }
}
