package com.project.myung.boostcampproject

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.http.*

interface MovieAPI {

    @GET("search/movie.json?")
    fun doGetMovieList(
        @Query("query") query: String,
        @Query("display") display: String,
        @Query("start") start: String,
        @Header("X-Naver-Client-Id") clientId: String,
        @Header("X-Naver-Client-Secret") clientSecret: String
    ): Observable<MovieModel>

}

class MovieModel(val items: ArrayList<MovieModelItem>)

class MovieModelItem(
    @field:SerializedName("title") val title: String,
    @field:SerializedName("link") val link: String,
    @field:SerializedName("image") val image: String,
    @field:SerializedName("subtitle") val subtitle: String,
    @field:SerializedName("pubDate") val pubDate: String,
    @field:SerializedName("director") val director: String,
    @field:SerializedName("actor") val actor: String,
    @field:SerializedName("userRating") val userRating: String
)
