package com.applab.applabstore.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import com.applab.applabstore.MyConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TsaiKunYu on 25/11/2014.
 */
public class MyRotateAnimator {

    public static void collapse(final ViewGroup vg){
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<Animator>();
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            v.setPivotX(v.getWidth() / 2);
            ObjectAnimator objAnim;
            if (i % 2 == 0) {
                //even
                v.setPivotY(0);
                objAnim = MyRotateAnimator.createAnimatorX(v, 0, -90);
            } else {
                //odd
//                                v.setPivotY(v.getHeight());
                v.setPivotY(0);
                objAnim = MyRotateAnimator.createAnimatorX(v, 0, 90);
            }

            ValueAnimator vAnim = ScaleAnimator.createAnimator(v, v.getHeight(), 0);
            list.add(objAnim);
            list.add(vAnim);
        }

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                vg.setVisibility(View.GONE);
            }
        });
        set.playTogether(list);
        set.start();
    }
    public static void expand(final ViewGroup vg , int childHeightPx){
        AnimatorSet set = new AnimatorSet();
        List<Animator> list = new ArrayList<Animator>();
        for (int i = 0; i < vg.getChildCount(); i++) {
            View v = vg.getChildAt(i);
            v.setPivotX(v.getWidth() / 2);
            ObjectAnimator objAnim;
            if (i % 2 == 0) {
                //even
                v.setPivotY(0);
                objAnim = MyRotateAnimator.createAnimatorX(v, -90, 0);
            } else {
                //odd
//                                v.setPivotY(v.getHeight());
                v.setPivotY(0);
                objAnim = MyRotateAnimator.createAnimatorX(v, 90, 0);
            }

            ValueAnimator vAnim = ScaleAnimator.createAnimator(v, 0, childHeightPx);
            list.add(objAnim);
            list.add(vAnim);
        }

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                vg.setVisibility(View.VISIBLE);
            }
        });

        set.playTogether(list);
        set.start();

    }
    public static ObjectAnimator createAnimatorY(View rootView, int startAng, int endAng){
        ObjectAnimator anim = ObjectAnimator.ofFloat(rootView, "rotationY", startAng, endAng);

        anim.setDuration(MyConstants.ANIMATION_DURATION);
        anim.setInterpolator(new AccelerateInterpolator());
        return anim;
    }

    public static ObjectAnimator createAnimatorX(final View rootView, int startAng, int endAng){
        final int height = rootView.getHeight();
        ObjectAnimator anim = ObjectAnimator.ofFloat(rootView, "rotationX", startAng, endAng);
        anim.setDuration(MyConstants.ANIMATION_DURATION);
        anim.setInterpolator(new AccelerateInterpolator());

//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                updateLayout(valueAnimator, rootView, height);
//            }
//        });

        return anim;
    }

    private static void updateLayout(ValueAnimator valueAnimator, View rootView, int originHeight){
        float f = (Float) valueAnimator.getAnimatedValue();
        if( f < 0 )
            f = -f;
        Log.i("LOG", "anim Value = " + f);

        double rad = Math.toRadians(f);
        double res = Math.cos(rad);
        Log.i("LOG","res = " + res);
        double resH = originHeight*res;

        rootView.getLayoutParams().height = (int)(resH);
        rootView.requestLayout();
    }

}
