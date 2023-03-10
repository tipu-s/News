package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.model.Article
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.viewModel.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment: Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val article = arguments?.getSerializable("article") as Article?
        webView.apply {
            webViewClient = WebViewClient()
            article?.url?.let { loadUrl(it) }
        }

        fab.setOnClickListener {
            article?.let {
                viewModel.insertArticle(it)
            }
            Snackbar.make(view,"Article saved", Snackbar.LENGTH_SHORT).show()
        }
    }


}