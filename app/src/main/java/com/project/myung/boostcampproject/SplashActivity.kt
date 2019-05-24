package com.project.myung.boostcampproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.myung.boostcampproject.moviesearch.MovieSearchActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MovieSearchActivity::class.java)
        startActivity(intent)

        finish()
    }
}
