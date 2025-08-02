package com.example.mealapp.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mealapp.R

@BindingAdapter("urlToImage")
fun urlToImage(imageView: ImageView,url : String) {

    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.loading)
        .into(imageView)
}