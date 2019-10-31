package com.sochic.sochic.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

@SuppressLint({"CheckResult"})
public class ScImage {


    public static void image( Object url, ImageView imageView) {
        Glide.with(imageView.getContext()).load(url).apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565).centerCrop().diskCacheStrategy(DiskCacheStrategy.NONE)).thumbnail(0.5f).into(imageView);
    }

    public static void cornerImage(Object url, int corner, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions().transforms(new Transformation[]{new CenterCrop(), new RoundedCorners(corner)});
        requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(imageView.getContext()).asBitmap().load(url).thumbnail(0.5f).apply(requestOptions).into(imageView);
    }

    public static void CircleImage( Object url, ImageView imageView) {
        RequestOptions requestOptions_circle = new RequestOptions().optionalCircleCrop();
        requestOptions_circle.diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(imageView.getContext()).asBitmap().load(url).apply(requestOptions_circle).thumbnail(0.5f).into(imageView);
    }


}
