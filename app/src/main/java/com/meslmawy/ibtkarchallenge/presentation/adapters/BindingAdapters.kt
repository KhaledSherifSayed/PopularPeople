
package com.meslmawy.ibtkarchallenge.presentation.adapters

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade

@BindingAdapter("loadImage")
fun bindLoadImage(view: AppCompatImageView, url: String) {
    val newUrl = "https://image.tmdb.org/t/p/w500/$url"
    Glide.with(view.context)
        .load(newUrl)
        .into(view)
}

