package com.fintech.denispok.fintechproject.students

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fintech.denispok.fintechproject.repository.Repository

class StudentsViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StudentsViewModel(repository) as T
    }
}
