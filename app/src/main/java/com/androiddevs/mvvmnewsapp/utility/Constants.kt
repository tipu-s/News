package com.androiddevs.mvvmnewsapp.utility

import com.androiddevs.mvvmnewsapp.R

class Constants {
    companion object {
        const val API_KEY = "3964fd8f2e49457e892799ba1e8badaa"
        const val BASE_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val TOTAL_QUERY_SIZE = 20

        val IMAGES = listOf(R.drawable.headline,
            R.drawable.headline_2,
            R.drawable.headline_3)
    }
}