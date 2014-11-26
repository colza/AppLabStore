package com.applab.applabstore.animator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by TsaiKunYu on 25/11/2014.
 */
public class MyRotateAnimator {

    public static ObjectAnimator createAnimatorY(View rootView, int startAng, int endAng){
        ObjectAnimator anim = ObjectAnimator.ofFloat(rootView, "rotationY", startAng, endAng);

        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateInterpolator());
        return anim;
    }

    public static ObjectAnimator createAnimatorX(final View rootView, int startAng, int endAng){
        final int height = rootView.getHeight();
        ObjectAnimator anim = ObjectAnimator.ofFloat(rootView, "rotationX", startAng, endAng);
        anim.setDuration(1000);
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
