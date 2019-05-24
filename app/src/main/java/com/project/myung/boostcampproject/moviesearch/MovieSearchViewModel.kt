package com.project.myung.boostcampproject.moviesearch

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.project.myung.boostcampproject.base.BaseViewModel
import com.project.myung.boostcampproject.data.model.Movies
import com.project.myung.boostcampproject.data.source.movie.MovieRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class MovieSearchViewModel : BaseViewModel() {
    val data = MutableLiveData<List<Movies.MovieModelItem>>()
    private var preQuery: String? = null
    private val subject = PublishSubject.create<String>()
    val isView = ObservableField<Boolean>(false)

    private val movieRepository = MovieRepository.getInstance()

    init {
        addDisposable(subject.debounce(250, TimeUnit.MILLISECONDS, Schedulers.computation())
            .filter { it.isNotEmpty() && it == preQuery }
            .flatMap { movieRepository.searchMovies(it, "20", "1").toObservable() }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                Log.e("loloerror", it.message)
            }
            .subscribe({
                if (it.items.isEmpty()) {
                    isView.set(true)
                } else {
                    isView.set(false)
                }
                data.value = it.items
            }, {Log.e("loloerror", it.message)})
        )
    }

    fun putQuery(query: String) {
        subject.onNext(query)
        preQuery = query
    }
}