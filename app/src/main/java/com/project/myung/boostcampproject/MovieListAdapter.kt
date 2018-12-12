package com.project.myung.boostcampproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import java.lang.Exception
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri


abstract class MovieListAdapter(val context: Context, val movieList: ArrayList<MovieInfo>) :
    RecyclerView.Adapter<ViewHolder>() {

    abstract fun load()

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_movie, p0, false))
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if ((position >= itemCount - 1))
            load()

        holder.title.text = Html.fromHtml(movieList[position].movieName)
        //holder.image.setImageDrawable(sList[position].icon)
        if (!movieList[position].icon.isEmpty()) {
            Picasso.get()
                .load(movieList[position].icon)
                .resize(220, 326)
                .centerCrop()
                .into(holder.image)
        }
        holder.rating.rating = movieList[position].rating
        holder.date.text = movieList[position].date
        holder.actor.text = movieList[position].actor
        holder.director.text = movieList[position].director
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movieList[position].link))
            context.startActivity(intent)
        }

    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val title = view.movie_title
    val rating = view.movie_rating
    val date = view.movie_date
    val actor = view.movie_actor
    val image = view.movie_image
    val director = view.movie_director
}

