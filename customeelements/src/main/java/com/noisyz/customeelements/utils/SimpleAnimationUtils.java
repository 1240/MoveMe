package com.noisyz.customeelements.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by Oleg on 25.02.2016.
 */
public class SimpleAnimationUtils {

    public static final int TOP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    public static void show(View view) {
        fadeIn(view, 0);
    }

    public static void hide(View view) {
        fadeOut(view, 0);
    }

    public static void fadeIn(final View view, long duration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1);
        animatorSet.play(animator);
        animatorSet.start();
    }

    public static void fadeOut(final View view, long duration) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(duration);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0);
        animatorSet.play(animator);
        animatorSet.start();
    }

    public static void hideOutsideOfScreen(final View view, final long duration, final int direction) {
        if (view != null) {
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);
            if (getPosition(view, direction) == 0) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        play(view, direction, animatorSet);
                    }
                });
            } else {
                play(view, direction, animatorSet);
            }
            fadeOut(view, duration);
        }
    }

    private static void play(final View view, final int direction, final AnimatorSet animatorSet) {
        ObjectAnimator moveToPosition = getAnimator(view, getPosition(
                        view, direction), direction
        );
        if (moveToPosition != null) {
            animatorSet.play(moveToPosition);
            animatorSet.start();
        }
    }

    private static float getPosition(final View view, int direction) {
        float position = 0;
        switch (direction) {
            case LEFT:
                position = -(view.getLeft() + view.getMeasuredWidth());
                break;
            case RIGHT:
                position = view.getRight() + view.getMeasuredWidth();
                break;
            case TOP:
                position = -(view.getTop() + view.getMeasuredHeight());
                break;
            case BOTTOM:
                position = (view.getBottom() + view.getMeasuredHeight());
                break;
        }
        return position;
    }

    private static ObjectAnimator getAnimator(final View view, float position, int direction) {
        ObjectAnimator moveToPosition = null;
        switch (direction) {
            case LEFT:
                moveToPosition = ObjectAnimator.ofFloat(view, "translationX", position);
                break;
            case RIGHT:
                moveToPosition = ObjectAnimator.ofFloat(view, "translationX", position);
                break;
            case TOP:
                moveToPosition = ObjectAnimator.ofFloat(view, "translationY", position);
                break;
            case BOTTOM:
                moveToPosition = ObjectAnimator.ofFloat(view, "translationY", position);
                break;
        }
        return moveToPosition;
    }

    public static void hideOutsideOfScreenNow(final View view, int direction) {
        hideOutsideOfScreen(view, 0, direction);
    }

    public static void hideOutsideOfScreenLeft(final View view, long duration) {
        hideOutsideOfScreen(view, duration, LEFT);
    }

    public static void hideOutsideOfScreenRight(final View view, long duration) {
        hideOutsideOfScreen(view, duration, RIGHT);
    }

    public static void hideOutsideOfScreenTop(final View view, long duration) {
        hideOutsideOfScreen(view, duration, TOP);
    }

    public static void hideOutsideOfScreenBottom(final View view, long duration) {
        hideOutsideOfScreen(view, duration, BOTTOM);
    }

    public static void hideOutsideOfScreenLeftNow(final View view) {
        hideOutsideOfScreenNow(view, LEFT);
    }

    public static void hideOutsideOfScreenRightNow(final View view) {
        hideOutsideOfScreenNow(view, RIGHT);
    }

    public static void hideOutsideOfScreenTopNow(final View view) {
        hideOutsideOfScreenNow(view, TOP);
    }

    public static void hideOutsideOfScreenBottomNow(final View view) {
        hideOutsideOfScreenNow(view, BOTTOM);
    }

    public static void showOutsideOfScreen(final View view, long duration) {
        if (view != null) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(duration);
            ObjectAnimator moveToStartX = ObjectAnimator.ofFloat(view, "translationX", 0);
            ObjectAnimator moveToStartY = ObjectAnimator.ofFloat(view, "translationY", 0);
            animatorSet.play(moveToStartX).with(moveToStartY);
            animatorSet.start();
            fadeIn(view, duration);
        }
    }

    public static void showOutsideOfScreenNow(final View view) {
        showOutsideOfScreen(view, 0);
    }


}
