package com.project.myung.boostcampproject.moviesearch
import androidx.recyclerview.widget.DiffUtil
import com.project.myung.boostcampproject.data.model.Movies

class MovieItemDiffUtilCallBack(private var newList: ArrayList<Movies.MovieModelItem>?,
                                 private var oldList: ArrayList<Movies.MovieModelItem>?) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList?.get(oldItemPosition)?.title == newList?.get(newItemPosition)?.title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList?.get(newItemPosition)?.pubDate == oldList?.get(oldItemPosition)?.pubDate &&
                newList?.get(newItemPosition)?.title == oldList?.get(oldItemPosition)?.title &&
                newList?.get(newItemPosition)?.actor == oldList?.get(oldItemPosition)?.actor &&
                newList?.get(newItemPosition)?.director == oldList?.get(oldItemPosition)?.director &&
                newList?.get(newItemPosition)?.image == oldList?.get(oldItemPosition)?.image &&
                newList?.get(newItemPosition)?.link == oldList?.get(oldItemPosition)?.link &&
                newList?.get(newItemPosition)?.subtitle == oldList?.get(oldItemPosition)?.subtitle
    }
}