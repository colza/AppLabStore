package com.applab.applabstore.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.ptr.folding.FoldingLayout;

/**
 * Created by TsaiKunYu on 25/11/2014.
 */
public class FoldingAnimator {
    public static final int FOLD_ANIMATION_DURATION = 1000;

    public static void expandFold(final FoldingLayout rootView) {
        Log.i("LOG", "expandFold");

        ObjectAnimator animator = createFoldAnimator(rootView, rootView.getFoldFactor(), 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rootView.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    public static void collapseFold(final FoldingLayout rootView) {
        Log.i("LOG", "collapseFold");
        ObjectAnimator animator = createFoldAnimator(rootView, rootView.getFoldFactor(), 1.0f);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    public static ObjectAnimator createFoldAnimator(final View rootView, float start, float end) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(rootView, "foldFactor", start, end);
        anim.setDuration(FOLD_ANIMATION_DURATION);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                int height = (Integer) valueAnimator.getAnimatedValue();
//                rootView.getLayoutParams().height = height;
//                rootView.requestLayout();

                Float f = (Float) valueAnimator.getAnimatedValue();
                Float result = 1.0f - f;
//                    ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) rootView.getLayoutParams();
//                    param.bottomMargin = -(int) (300 * f);

//                int transHeight = (int)(f*300);
//                ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams)mBottomImg.getLayoutParams();
//                param.topMargin = -transHeight;
//                mBottomImg.requestLayout();

//                int bottom = rootView.getBottom();
//                bottom = bottom - (int) (f * 300);
//                Log.i("LOG", "Bottom = " + bottom);
//                rootView.layout(rootView.getLeft(), rootView.getTop(), rootView.getRight(), bottom);
//                    ViewGroup.LayoutParams param = rootView.getLayoutParams();
//                    param.height = (int) (300 * result);
//
//                    rootView.requestLayout();

            }
        });

        return anim;
    }
}
