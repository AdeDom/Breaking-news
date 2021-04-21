package com.adedom.breakingnews.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.adedom.breakingnews.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadCircle(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_search_error
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .circleCrop()
        .into(this)
}

fun ImageView.load(
    url: String?,
    @DrawableRes placeholder: Int = R.drawable.ic_search_error
) {
    Glide.with(this)
        .load(url)
        .apply(RequestOptions.placeholderOf(placeholder))
        .into(this)
}
