package com.androiddevs.mvvmnewsapp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.Article
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedNewsViewModel(
    private val repository: NewsRepository
): ViewModel() {
    private val _savedArticle = MutableStateFlow<List<Article>>(emptyList())
    val savedArticle: StateFlow<List<Article>> = _savedArticle

    init {
        getSavedArticles()
    }

    private fun getSavedArticles() = viewModelScope.launch {
        repository.getSavedArticles().collectLatest {
            _savedArticle.value = it
        }
    }

    fun insertArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }
    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }
}