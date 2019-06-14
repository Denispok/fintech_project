package com.fintech.denispok.fintechproject.ui.courses

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fintech.denispok.fintechproject.repository.Repository

class CoursesViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CoursesViewModel(repository) as T
    }
}
