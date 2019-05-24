package com.project.myung.boostcampproject.moviesearch

import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.myung.boostcampproject.R
import com.project.myung.boostcampproject.base.BaseActivity
import com.project.myung.boostcampproject.data.model.Movies
import com.project.myung.boostcampproject.databinding.MovieSearchActivityBinding
import android.content.Intent
import android.net.Uri


class MovieSearchActivity : BaseActivity<MovieSearchActivityBinding>() {
    override val layoutResourceId: Int
        get() = R.layout.movie_search_activity

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewDataBinding.root)
        viewDataBinding.viewmodel = viewModel

        initView()
        ovbserveItem()
    }

    private fun initView() {
        viewDataBinding.rvSearchList.layoutManager = LinearLayoutManager(this)
        viewDataBinding.rvSearchList.adapter = MovieSearchListAdapter { item ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
            startActivity(intent)
        }
        viewDataBinding.searchView.onActionViewExpanded()
        viewDataBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.putQuery(query) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.putQuery(newText) }
                return false
            }
        })
    }

    private fun ovbserveItem() {
        viewModel.data.observe(this, Observer {
            (viewDataBinding.rvSearchList.adapter as MovieSearchListAdapter).updateListItems(it as ArrayList<Movies.MovieModelItem>)
        })
    }
}
