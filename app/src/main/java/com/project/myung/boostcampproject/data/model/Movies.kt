package com.project.myung.boostcampproject.data.model

data class Movies(val items: ArrayList<MovieModelItem>) {
    data class MovieModelItem(
        val title: String,
        val link: String,
        val image: String,
        val subtitle: String,
        val pubDate: String,
        val director: String,
        val actor: String,
        val userRating: String
    )
}

