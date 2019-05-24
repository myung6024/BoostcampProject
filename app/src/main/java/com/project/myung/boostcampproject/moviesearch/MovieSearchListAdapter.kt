package com.project.myung.boostcampproject.moviesearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.myung.boostcampproject.data.model.Movies
import com.project.myung.boostcampproject.databinding.ItemMovieBinding

class MovieSearchListAdapter(private val listener: (item: Movies.MovieModelItem) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val movieItemList = ArrayList<Movies.MovieModelItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MovieItemViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).binding.item = movieItemList[position]
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener { listener.invoke(movieItemList[position]) }
    }

    override fun getItemCount(): Int {
        return movieItemList.size
    }

    //기록된 활동들에 대한 뷰홀더
    class MovieItemViewHolder(var binding: ItemMovieBinding,
                              val listener: (item: Movies.MovieModelItem) -> Unit) : RecyclerView.ViewHolder(binding.root)

    fun updateListItems(movieItem: ArrayList<Movies.MovieModelItem>) {
        val diffCallback = MovieItemDiffUtilCallBack(movieItem, movieItemList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        movieItemList.clear()
        movieItemList.addAll(movieItem)
        diffResult.dispatchUpdatesTo(this)
    }
}