package com.project.myung.boostcampproject

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_movie.view.*
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics

abstract class MovieListAdapter(private val context: Context, private val movieList: ArrayList<MovieModelItem>) :
    RecyclerView.Adapter<ViewHolder>() {

    //무한 스크롤 함수를 위한 함수
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
        //현재 위치가 아이템 갯수와 일치하다면 마지막 포인트로 인지하고 다음 데이터 로드
        if ((position >= itemCount - 1))
            load()

        holder.title.text = Html.fromHtml(movieList[position].title)

        //피카소 라이브러리를 이용한 url로 이미지 불러오기
        //해상도 변화에 따라 이미지의 크기를 같게 하기 위하여 dp 크기를 픽셀로 변환하여 값 대입
        if (movieList[position].image.isNotEmpty()) {
            holder.image.visibility = View.VISIBLE
            Picasso.get()
                .load(movieList[position].image)
                .placeholder(R.color.transparent)
                .resize(convertDpToPixel(110f,context), convertDpToPixel(168f,context))
                .centerCrop()
                .into(holder.image)
        }else{
            holder.image.visibility = View.INVISIBLE
        }

        holder.rating.rating = (movieList[position].userRating.toFloat() / 2f)
        holder.date.text = movieList[position].pubDate

        holder.actor.text = movieList[position].actor.replace("|",",").let { if(it.isEmpty()) null else it.substring(0,it.length - 1) }

        holder.director.text = movieList[position].director.replace("|",",").let { if(it.isEmpty()) null else it.substring(0,it.length - 1) }

        //해당 아이템을 클릭하면 링크로 이동
        holder.itemView.setOnClickListener {
            if(movieList[position].link.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(movieList[position].link))
                context.startActivity(intent)
            }
        }

    }

    /*dp를 픽셀로 변환해주는 함수*/
    private fun convertDpToPixel(dp: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.movie_title
    val rating = view.movie_rating
    val date = view.movie_date
    val actor = view.movie_actor
    val image = view.movie_image
    val director = view.movie_director
}

