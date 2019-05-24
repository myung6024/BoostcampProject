package com.project.myung.boostcampproject.data.source.movie

import com.project.myung.boostcampproject.data.model.Movies
import io.reactivex.Single

interface MovieDataSource {
    fun searchMovies(
        query: String,
        display: String,
        start: String
    ): Single<Movies>
}
