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
public class BlurImageView extends ImageView {

    private int width, height;
    private Handler handler = new Handler();


    public BlurImageView(Context context) {
        super(context);
        init();
    }

    public BlurImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BlurImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();
            }
        });
    }

    public void setImageBitmap(Bitmap bitmap) {
        new Thread(new BlurRunnable(bitmap)).start();
    }

    private class BlurRunnable implements Runnable {
        private Bitmap bitmap;

        public BlurRunnable(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            bitmap = SimpleImageUtils.fastBlur(bitmap, 10, 5);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    SimpleAnimationUtils.fadeIn(BlurImageView.this, 500);
                    BlurImageView.super.setImageBitmap(bitmap);
                }
            });
        }
    }


}
