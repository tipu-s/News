package com.androiddevs.mvvmnewsapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.androiddevs.mvvmnewsapp.repository.NewsRepository

class SavedNewsViewModelProviderFactory(
    private val repository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SavedNewsViewModel(repository) as T
    }
}