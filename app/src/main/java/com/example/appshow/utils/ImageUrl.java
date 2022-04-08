package com.example.appshow.utils;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.appshow.R;

public class ImageUrl {
    @BindingAdapter("android:imageUrl")
    public static void setImageUrl(@NonNull ImageView imageView, String URL) {
        imageView.setAlpha(0f);
        if (URL != null) {
            Glide.with(imageView.getContext()).load(URL).placeholder(R.drawable.image_no).into(imageView);
            imageView.animate().setDuration(300).alpha(1f).start();
        } else {
            imageView.setImageResource(R.drawable.image_no);
        }
    }
}
