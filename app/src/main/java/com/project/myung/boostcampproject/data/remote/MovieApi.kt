package com.project.myung.boostcampproject.data.remote

import com.project.myung.boostcampproject.data.model.Movies
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {
    @GET("search/movie.json")
    fun searchMovies(
        @Query("query") query: String,
        @Query("display") display: String,
        @Query("start") start: String
    ): Single<Movies>
}