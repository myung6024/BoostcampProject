package com.project.myung.boostcampproject.data.source.movie

import com.project.myung.boostcampproject.data.model.Movies
import io.reactivex.Single

class MovieRepository private constructor() : MovieDataSource {

    private val remoteDataSource: MovieRemoteDataSource = MovieRemoteDataSource.getInstance()

    override fun searchMovies(query: String, display: String, start: String): Single<Movies> {
        return remoteDataSource.searchMovies(query, display, start)
    }

    companion object {
        @Volatile
        private var INSTANCE: MovieRepository? = null

        @JvmStatic
        fun getInstance() =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: MovieRepository().also { INSTANCE = it }
            }
    }
}