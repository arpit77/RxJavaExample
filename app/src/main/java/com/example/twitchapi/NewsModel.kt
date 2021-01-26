package com.example.twitchapi

data class NewsModel(val status: String, val totalResults: Int, val articles: ArrayList<Article>) {

    data class Source(var id: String,  var name: String) {

    }

    data class Article(val source: Source, val author: String, val title: String, val description: String,
                       val url: String, val urlToImage: String, val publishedAt: String, val content: String) {

    }

}
