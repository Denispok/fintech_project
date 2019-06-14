package com.fintech.denispok.fintechproject.ui.courses.rating

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.fintech.denispok.fintechproject.repository.Repository

class RatingViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RatingViewModel(repository) as T
    }
}
