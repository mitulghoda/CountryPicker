package com.mitul.countrypicker.bottomsheet.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.mitul.countrypicker.R;

public class GlideUtil {
    private static RequestOptions defaultRequestOption;

    public static RequestOptions getDefaultRequestOption() {
        if (defaultRequestOption == null) {
            return defaultRequestOption = BuildRequestOptionRounded(R.drawable.ic_launcher_foreground);
        }
        return defaultRequestOption;
    }
    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).apply(GlideUtil.getDefaultRequestOption()).into(view);
    }
    public static RequestOptions BuildRequestOptionRounded(int image_holder_portrait) {
        return new RequestOptions().placeholder(image_holder_portrait)
                .error(image_holder_portrait)
                .fallback(image_holder_portrait)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }
}