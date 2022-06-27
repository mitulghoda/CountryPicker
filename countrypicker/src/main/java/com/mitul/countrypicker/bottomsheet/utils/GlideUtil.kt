package com.mitul.countrypicker.bottomsheet.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mitul.countrypicker.R

object GlideUtil {
    var defaultRequestOption: RequestOptions? = null
        get() = if (field == null) {
            BuildRequestOptionRounded(R.drawable.ic_launcher_foreground)
                .also { field = it }
        } else field
        private set

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imageUrl: String?) {
        Glide.with(view.context).load(imageUrl).apply(defaultRequestOption!!).into(view)
    }

    private fun BuildRequestOptionRounded(image_holder_portrait: Int): RequestOptions {
        return RequestOptions().placeholder(image_holder_portrait)
            .error(image_holder_portrait)
            .fallback(image_holder_portrait)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    }
}