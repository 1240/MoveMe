package ru.moleculus.moveme.customview;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.noisyz.customeelements.R;
import com.noisyz.databindinglibrary.callback.imageproperty.impl.AsyncImageProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ru.moleculus.moveme.net.ApiConst;

/**
 * Created by Oleg on 18.03.2016.
 */
public class MoveMeImageProvider extends AsyncImageProvider<String> implements Target {

    private String imageUrl = ApiConst.EMPTY_DATA;

    @Override
    public void loadBitmap(String s) {
        if (!imageUrl.equals(s)) {
            Picasso picasso = new Picasso.Builder(getView().getContext()).build();
            picasso.load(s).centerCrop().
                    resizeDimen(R.dimen.navigation_drawer_width, R.dimen.navigation_drawer_width).
                    error(R.drawable.empty_user).into(this);
            imageUrl = s;
        }
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        showBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        onBitmapLoaded(((BitmapDrawable) errorDrawable).getBitmap(), null);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
