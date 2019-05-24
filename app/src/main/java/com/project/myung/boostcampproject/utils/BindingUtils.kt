package com.project.myung.boostcampproject.utils

import android.content.Context
import android.text.Html
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.project.myung.boostcampproject.R
import com.squareup.picasso.Picasso

object BindingUtils {
    private fun convertDpToPixel(dp: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    @JvmStatic
    @BindingAdapter("app:image")
    fun setImage(imageView: ImageView, path: String?) {
        if (!path.isNullOrEmpty()) {
            imageView.visibility = View.VISIBLE
            Picasso.get()
                .load(path)
                .placeholder(R.color.transparent)
                .resize(convertDpToPixel(110f, imageView.context), convertDpToPixel(168f, imageView.context))
                .centerCrop()
                .into(imageView)
        } else {
            imageView.visibility = View.INVISIBLE
        }
    }

    @JvmStatic
    @BindingAdapter("app:htmlText")
    fun setHtmlText(textView: TextView, text: String?) {
        text?.let { textView.text = Html.fromHtml(text) }
    }

    @JvmStatic
    @BindingAdapter("app:arrayText")
    fun setArrayText(textView: TextView, text: String?) {
        if (text.isNullOrEmpty()) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
            textView.text = text.replace("|", ",").let { if (it.isEmpty()) null else it.substring(0, it.length - 1) }
        }
    }

    @JvmStatic
    @BindingAdapter("app:rating")
    fun setRating(ratingBar: RatingBar, rating: String) {
        ratingBar.rating = rating.toFloat() / 2
    }
}