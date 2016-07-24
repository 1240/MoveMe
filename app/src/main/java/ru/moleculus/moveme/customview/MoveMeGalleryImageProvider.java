package ru.moleculus.moveme.customview;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;

/**
 * Created by Oleg on 18.03.2016.
 */
public class MoveMeGalleryImageProvider extends MoveMeImageProvider {
    @Override
    public void loadBitmap(String s) {
        new Picasso.Builder(getView().getContext()).build();
            Picasso.with(getView().getContext()).load(s).into(this);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        Log.d("myLogs", "OnBitmapLoadedfailed");
    }


}
