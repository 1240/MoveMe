package ru.moleculus.moveme.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.noisyz.customeelements.utils.SimpleAnimationUtils;
import com.noisyz.customeelements.utils.SimpleImageUtils;

/**
 * Created by Oleg on 02.03.2016.
 */
public class RoundedImageView extends ImageView {

    private int width, height;
    private Handler handler = new Handler();


    public RoundedImageView(Context context) {
        super(context);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init(){
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap){
        new Thread(new RoundImageRunnable(bitmap)).start();
    }

    private class RoundImageRunnable implements Runnable{
        private Bitmap bitmap;
        public RoundImageRunnable(Bitmap bitmap){
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            bitmap = SimpleImageUtils.getCircleBitmapWithWhiteBorder(bitmap, width);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeIn(RoundedImageView.this, 500);
                    RoundedImageView.super.setImageBitmap(bitmap);
                }
            });
        }
    }


}
