package com.fintech.denispok.fintechproject.lectures

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fintech.denispok.fintechproject.repository.Repository

class LecturesViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LecturesViewModel(repository) as T
    }
}
