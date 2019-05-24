package com.project.myung.boostcampproject.data.source.movie

import com.project.myung.boostcampproject.data.model.Movies
import com.project.myung.boostcampproject.data.remote.MovieAPI
import com.project.myung.boostcampproject.data.remote.RetrofitClient
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieRemoteDataSource private constructor() : MovieDataSource {

    override fun searchMovies(query: String, display: String, start: String): Single<Movies> {
        return RetrofitClient.client.create(MovieAPI::class.java)
            .searchMovies(query, display, start)
            .subscribeOn(Schedulers.newThread())
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieRemoteDataSource? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MovieRemoteDataSource().also { INSTANCE = it }
            }
    }

}
