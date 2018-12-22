package com.arjit.githubprviewer.ui.databinding

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("imageSrcToCircle", "placeholder")
    fun setImageViewFromUrlAndTransformToCircle(
        imageView: ImageView, imageUrl: String,
        placeholder: Drawable
    ) {

        Glide.with(imageView.context.applicationContext)
            .load(imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .apply(RequestOptions.errorOf(placeholder))
            .into(imageView)
    }

    @JvmStatic
    @BindingAdapter("bgTint")
    fun tintBackground(view: View, color: Int) {
        view.background?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
