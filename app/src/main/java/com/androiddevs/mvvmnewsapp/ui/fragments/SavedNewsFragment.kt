package com.androiddevs.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androiddevs.mvvmnewsapp.R
import com.androiddevs.mvvmnewsapp.adapter.NewsAdapter
import com.androiddevs.mvvmnewsapp.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.ui.NewsActivity
import com.androiddevs.mvvmnewsapp.ui.viewModel.NewsViewModel
import com.androiddevs.mvvmnewsapp.ui.viewModel.NewsViewModelProviderFactory
import com.androiddevs.mvvmnewsapp.ui.viewModel.SavedNewsViewModel
import com.androiddevs.mvvmnewsapp.ui.viewModel.SavedNewsViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: SavedNewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = NewsRepository(ArticleDatabase(context!!))
        val viewModelProviderFactory = SavedNewsViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SavedNewsViewModel::class.java)

        setupRecyclerView()
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, "Deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.insertArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelper).apply {
            attachToRecyclerView(rvSavedNews)
        }

        lifecycleScope.launch {
            viewModel.savedArticle.collectLatest {
                newsAdapter.differ.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}