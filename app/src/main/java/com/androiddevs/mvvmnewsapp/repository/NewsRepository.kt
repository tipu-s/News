package com.androiddevs.mvvmnewsapp.repository

import androidx.lifecycle.LiveData
import com.androiddevs.mvvmnewsapp.api.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.model.Article
import retrofit2.Retrofit

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getAllNews(queryString: String, pageNumber: Int) =
        RetrofitInstance.api.getAllNews(queryString, pageNumber)

    suspend fun insertArticle(article: Article) = db.getArticleDao().insertArticle(article)
    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
    fun getSavedArticles() = db.getArticleDao().getSavedArticles()
}