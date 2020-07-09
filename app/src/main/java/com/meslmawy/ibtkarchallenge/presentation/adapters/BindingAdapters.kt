
package com.meslmawy.ibtkarchallenge.presentation.adapters

import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.meslmawy.ibtkarchallenge.domain.dto.Movies
import de.hdodenhof.circleimageview.CircleImageView

@BindingAdapter("loadImage")
fun bindLoadImage(view: AppCompatImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("loadCircleImage")
fun bindLoadImage(view: CircleImageView, url: String) {
    Glide.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("loadName")
fun bindloadName(view: TextView, movie: Movies) {
   if(movie.original_name.isNullOrEmpty())
       view.text = movie.title
    else
       view.text = movie.original_name
}