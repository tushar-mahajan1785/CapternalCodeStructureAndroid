package com.capternal.test.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.capternal.test.R;

import java.io.File;

public class ImageLoader {

    public static void loadWithCircular(final Context context, String path, final ImageView imageView) {
        Glide.with(context).load(path).asBitmap().placeholder(R.drawable.food_placeholder).placeholder(R.drawable.food_placeholder).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadWithCircular(final Context context, File file, final ImageView imageView) {
        Glide.with(context).load(file).asBitmap().placeholder(R.drawable.food_placeholder).placeholder(R.drawable.food_placeholder).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadWithCircular(final Context context, int resource, final ImageView imageView) {
        Glide.with(context).load(resource).asBitmap().placeholder(R.drawable.food_placeholder).override(150, 150).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadWithCircular(final Context context, Uri uri, final ImageView imageView) {
        Glide.with(context).load(uri).asBitmap().placeholder(R.drawable.food_placeholder).override(150, 150).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public static void loadWithRectangleResize(final Context context, String path, final ImageView imageView, int width, int height) {
        Glide.with(context)
                .load(path)
                .override(width, height) // Resize the image to these dimensions (in pixel). does not respect aspect ratio
                .into(imageView);
    }


    public static void loadWithRectangle(final Context context, String path, final ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

    public static void loadWithRectangle(final Context context, Uri uri, final ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    public static void loadWithRectangle(final Context context, File file, final ImageView imageView) {
        Glide.with(context).load(file).into(imageView);
    }

    public static void loadWithRectangle(final Context context, int resource, final ImageView imageView) {
        Glide.with(context).load(resource).into(imageView);
    }


}