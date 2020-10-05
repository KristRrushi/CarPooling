package krist.car.utils;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class Animations {
    public static void animateViewHHeightTopToBottom(View viewToAnimate, int maxheight) {
        ValueAnimator anim = ValueAnimator.ofInt(viewToAnimate.getMeasuredHeight(), maxheight);
        anim.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = viewToAnimate.getLayoutParams();
            layoutParams.height = val;
            viewToAnimate.setLayoutParams(layoutParams);
        });
        anim.setDuration(800);
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.start();
    }

    public static void animateViewHHeightBottomToTop(View viewToAnimate, int minHeight) {
        ValueAnimator anim = ValueAnimator.ofInt(viewToAnimate.getMeasuredHeight(), minHeight);
        anim.addUpdateListener(animation -> {
            int val = (Integer) animation.getAnimatedValue();
            ViewGroup.LayoutParams layoutParams = viewToAnimate.getLayoutParams();
            layoutParams.height = val;
            viewToAnimate.setLayoutParams(layoutParams);
        });
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewToAnimate.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.setInterpolator(new FastOutSlowInInterpolator());
        anim.setDuration(800);
        anim.start();
    }
}
