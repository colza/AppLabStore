package com.applab.applabstore.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;

import com.applab.applabstore.MyConstants;

/**
 * Created by TsaiKunYu on 25/11/2014.
 *
 */
public class ExpandCollapseAnimator {

    public static void expand(final View rootView, int originHeight) {
        rootView.setVisibility(View.VISIBLE);

        ValueAnimator anim = createAnimator(rootView, 0, originHeight);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.setVisibility(View.VISIBLE);
            }
        });
        anim.start();
    }

    public static void collapse(final View rootView) {

        ValueAnimator anim = createAnimator(rootView, rootView.getHeight(), 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.setVisibility(View.GONE);

                rootView.getLayoutParams().height = -2;
                rootView.requestLayout();

            }
        });

        anim.start();
    }

    public static ValueAnimator createAnimator(final View rootView, int start, int end) {
        ValueAnimator anim = ValueAnimator.ofInt(start, end);
        anim.setDuration(MyConstants.ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int height = (Integer) valueAnimator.getAnimatedValue();
                rootView.getLayoutParams().height = height;
                rootView.requestLayout();
            }
        });

        return anim;
    }
}
