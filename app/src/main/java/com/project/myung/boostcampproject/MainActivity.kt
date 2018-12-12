package com.project.myung.boostcampproject

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import com.victor.loading.book.BookLoading



data class MovieInfo(
    val movieName: String,
    val rating: Float,
    val date: String,
    val director: String,
    val actor: String,
    val icon: String,
    val link: String
)

class MainActivity : AppCompatActivity() {
    private val api by lazy { provideMovieApi() }
    private val disposable = CompositeDisposable()
    private val movieList = ArrayList<MovieInfo>()
    private val displayInt = "15"
    private val clientId = "2pWW9zVLX5zp0SpCoBXd"
    private val clientSecret = "nh0_3eHf4o"
    private val movieAdapter = object : MovieListAdapter(this, movieList) {
        override fun load() {
            if (canScroll) {
                startPosition += 15
                getMovieData(nowQuery, startPosition.toString())
            }
        }
    }
    private var startPosition = 1
    private var lastMovieLink = ""
    private var canScroll = false
    private var nowQuery = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //검색 버튼이 눌리면 모두 초기화
        search_button.setOnClickListener {
            if (!search_edit_frame.text.isEmpty()) {
                movieList.clear()
                lastMovieLink = ""
                startPosition = 1
                canScroll = true
                nowQuery = search_edit_frame.text.toString()
                imm.hideSoftInputFromWindow(search_button.windowToken, 0);
                getMovieData(nowQuery, startPosition.toString())
            }else{
                Toast.makeText(this@MainActivity, "검색어를 입력하셔야 합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        val mLayoutManager = LinearLayoutManager(this)
        movie_recycle.layoutManager = mLayoutManager
        movie_recycle.adapter = movieAdapter
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    //API에서 영화 데이터를 받아 리스트에 추가하는 함수
    private fun getMovieData(query: String, startPoint: String) {//검색어, 무한 스크롤을 위한 검색 포인트
        progressStart()
        disposable.add(api.doGetMovieList(query, displayInt, startPoint, clientId, clientSecret)
            // REST API 를 통해 받은 응답에서 Result 값을 추출합니다.
            .flatMap {
                Observable.just(it.items)
            }

            // 이 이후에 수행되는 코드는 모두 메인 스레드에서 실행합니다.
            .observeOn(AndroidSchedulers.mainThread())

            // 구독할 때 수행할 작업을 구현합니다.
            .doOnSubscribe { }

            // 스트림이 종료될 때 수행할 작업을 구현합니다.
            .doOnTerminate { }

            // 옵서버블을 구독합니다.
            .subscribe({ data ->
                //data사이즈 확인
                if (data.size != 0) {
                    //중복된 데이터를 불러온 것이 아닌지 검사
                    if (data.last().link == lastMovieLink) {
                        canScroll = false
                    } else { //아니라면 해당 데이터를 리스트에 추가하고 갱신시킴
                        lastMovieLink = data.last().link
                        data.map {
                            Log.d("MyTag", it.image)
                            movieList.add(
                                MovieInfo(
                                    it.title,
                                    it.userRating.toFloat() / 2,
                                    it.pubDate,
                                    it.director,
                                    it.actor,
                                    it.image,
                                    it.link
                                )
                            )
                        }
                        movieAdapter.notifyDataSetChanged()
                    }
                } else { //데이터가 0이면 결과 없음 출력
                    Toast.makeText(this@MainActivity, "결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    movieAdapter.notifyDataSetChanged()
                }

                // data 를 받아 처리합니다.
                // 작업 중 오류가 발생하면 이 블록은 호출되지 않습니다.
                progressStop()
                disposable.clear()

            }) {
                // 에러블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출됩니다.
                Toast.makeText(this@MainActivity, "${it.message}", Toast.LENGTH_SHORT).show()
                progressStop()
                disposable.clear()
            })
    }

    override fun onDestroy() {
        super.onDestroy()

        // Activity 가 종료되면
        // 사용중인 통신을 중단합니다.
        disposable.clear()
    }

    fun progressStart(){
        rotateloading.visibility = View.VISIBLE
        rotateloading.start()
    }
    fun progressStop(){
        rotateloading.stop()
        rotateloading.visibility = View.GONE
    }
}
