package com.applab.applabstore.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applab.applabstore.Models.ModelAppType;
import com.applab.applabstore.MyActivity;
import com.applab.applabstore.MyConstants;
import com.applab.applabstore.R;
import com.applab.applabstore.adapter.AdapterAppType;
import com.applab.applabstore.animator.FoldingAnimator;
import com.applab.applabstore.animator.MyRotateAnimator;
import com.applab.applabstore.animator.ScaleAnimator;
import com.ptr.folding.BaseFoldingLayout;
import com.ptr.folding.FoldingLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.herxun.library.singleton.StResol;

/**
 * Created by TsaiKunYu on 25/11/2014.
 */
public class ViewIncludeFoldLayout extends LinearLayout {
    private static View sCurrentVisibleFold;

    public ViewAppType mHeadView;
    //    public FoldingLayout mFold;
    private AppListView mAppListView;
    private MyLinear mMyLin;
    private StResol mRel;

//    public ViewIncludeFoldLayout(Context context) {
//        super(context);
//        mRel = StResol.getInstance(context);
//        setOrientation(VERTICAL);
//
//        mHeadView = new ViewAppType(context);
//        mHeadView.setId(mRel.id++);
//
//        mFold = new FoldingLayout(context);
//
//        mMyLin = new MyLinear(context, new String[]{"One","Two","Three"});
//        mAppListView = new AppListView(context);
//
////        ImageView view = new ImageView(context);
////        view.setImageResource(R.drawable.ic_launcher);
////        view.setBackgroundColor(Color.BLUE);
////        mFold.addView(view);
//        mFold.setOrientation(BaseFoldingLayout.Orientation.VERTICAL);
////        mFold.addView(mAppListView);
//        mFold.addView(mMyLin);
//
//        addView(mHeadView);
//
//        mFold.setVisibility(View.GONE);
//        addView(mFold, mRel.mUI.mLayout.linParam(-1, 0));
//    }

    public ViewIncludeFoldLayout(Context context) {
        super(context);

        mRel = StResol.getInstance(context);
        setOrientation(VERTICAL);
        setBackgroundColor(Color.GRAY);

        mHeadView = new ViewAppType(context);
        mHeadView.setId(mRel.id++);

//        mFold = new FoldingLayout(context);

        mMyLin = new MyLinear(context, new String[]{"One","Two","Three"});
        mMyLin.setVisibility(View.GONE);

        mAppListView = new AppListView(context);
        mAppListView.setVisibility(View.GONE);
//        ImageView view = new ImageView(context);
//        view.setImageResource(R.drawable.ic_launcher);
//        view.setBackgroundColor(Color.BLUE);
//        mFold.addView(view);
//        mFold.setOrientation(BaseFoldingLayout.Orientation.VERTICAL);
//        mFold.addView(mAppListView);
//        mFold.addView(mMyLin);

        addView(mHeadView);
//        addView(mAppListView);

        addView(mMyLin);

//        mFold.setVisibility(View.GONE);
//        addView(mFold, mRel.mUI.mLayout.linParam(-1, 0));
    }

    private int originHeight = 100;
    public class MyLinear extends LinearLayout {
        StResol stRel;
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//            Log.i("LOG","onMeasure h = " + heightMeasureSpec);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
//            Log.i("LOG","onSize h = " + h);
        }

        public MyLinear(Context context, String[] name) {
            super(context);
            stRel = StResol.getInstance(context);
            setOrientation(VERTICAL);
        }

        public void attachChildView(int index){
            String[] name = getResources().getStringArray(R.array.subtitle);

            for (int i = 0; i <= index; i++) {
                TextView tv = stRel.mUI.mTv.textInit(getContext(), 35, Color.BLACK, null, stRel.id++, name[i]);
                tv.setGravity(Gravity.CENTER_VERTICAL);
                tv.setPadding(20,0,0,0);
                tv.setBackgroundColor(Color.WHITE);
                addView(tv, stRel.mUI.mLayout.linParam(-1, originHeight));
            }
        }
    }

    public void setData(ModelAppType data) {
        mHeadView.setData(data);
        mMyLin.attachChildView(data.index);
//        mAppListView.refreshList(data.appListStr);

        mHeadView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if( mMyLin.getTag(R.id.is_anim_running) != null && (Boolean)mMyLin.getTag(R.id.is_anim_running)){
                    return;
                }

                int count = mMyLin.getChildCount();
//                int count = mAppListView.getAdapter().getCount();
                int height = StResol.getInstance(view.getContext()).mResolK.szPDtoPC(originHeight) * count;

                final ViewGroup vg = mMyLin;
                vg.clearAnimation();
                if (vg.getVisibility() == View.GONE ) {
                    switch(MyConstants.sExpandType){
                        case MyConstants.FoldExpand:
                            MyRotateAnimator.expand(vg, mRel.mResolK.szPDtoPC(originHeight));
                            if( sCurrentVisibleFold != null && sCurrentVisibleFold != vg){
                                MyRotateAnimator.collapse((ViewGroup)sCurrentVisibleFold);
                            }
                            break;
                        case MyConstants.ScaleExpand:
                            if (sCurrentVisibleFold != null && sCurrentVisibleFold != vg) {
                                ScaleAnimator.collapse(sCurrentVisibleFold);
                            }
                            ScaleAnimator.expand(vg, height);

                            break;
                    }
                    sCurrentVisibleFold = vg;
                } else {
                    switch(MyConstants.sExpandType){
                        case MyConstants.FoldExpand:
                            MyRotateAnimator.collapse(vg);
                            break;
                        case MyConstants.ScaleExpand:
                            ScaleAnimator.collapse(vg);
                            break;
                    }
                    sCurrentVisibleFold = null;
                }

            }
        });
    }

//    public void setData(ModelAppType data) {
//        mHeadView.setData(data);
////        mAppListView.refreshList(data.appListStr);
//
//        mHeadView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                int count = mMyLin.getChildCount();
////                int count = mAppListView.getAdapter().getCount();
//                int height = StResol.getInstance(view.getContext()).mResolK.szPDtoPC(100) * count;
//
//                if (mFold.getVisibility() == View.GONE) {
//                    Log.i("LOG", "expand");
//
//                    if (MyActivity.sIsFoldingEffect) {
////                        mFold.getLayoutParams().height = height;
////                        FoldingAnimator.expandFold(mFold);
////                        FoldingAnimator.expandFold(mFold, height);
//
//                        View view0 = mAppListView.getChildAt(0);
//                        view0.setPivotY(0);
//                        view0.setPivotX(view0.getWidth()/2);
//                        ObjectAnimator objAnim = MyRotateAnimator.createAnimatorX(view0, -90, 0);
//                        ValueAnimator vAnim = ScaleAnimator.createAnimator(view0, 0, view0.getHeight());
//                        AnimatorSet set = new AnimatorSet();
//                        set.playTogether(objAnim, vAnim);
//                        set.start();
//
//                    } else {
//
//                        if (sCurrentVisibleFold != null && sCurrentVisibleFold != mFold) {
//                            ScaleAnimator.collapse(sCurrentVisibleFold);
//                        }
//
//                        ScaleAnimator.expand(mFold, height);
//                    }
//
//                    sCurrentVisibleFold = mFold;
////                        collapseFold(mFold);
////                        expand(mFold);
////                    expandFold(mFold);
////                        animateFold(mFold, 1000);
//                } else {
//                    Log.i("LOG", "collapse");
//                    if (MyActivity.sIsFoldingEffect) {
//
////                        FoldingAnimator.collapseFold(mFold);
//                        View view0 = mAppListView.getChildAt(0);
//                        view0 = mMyLin.getChildAt(0);
//                        view0.setPivotY(0);
//                        view0.setPivotX(view0.getWidth()/2);
//                        ObjectAnimator objAnim = MyRotateAnimator.createAnimatorX(view0, 0, -90);
//                        ValueAnimator vAnim = ScaleAnimator.createAnimator(view0, view0.getHeight(),0 );
//                        AnimatorSet set = new AnimatorSet();
//                        set.playTogether(objAnim, vAnim);
//                        set.start();
//
//                        View view1 = mAppListView.getChildAt(1);
//                        view1 = mMyLin.getChildAt(1);
//                        view1.setPivotY(0);
//                        view1.setPivotX(view1.getWidth()/2);
//                        ObjectAnimator objAnim1 = MyRotateAnimator.createAnimatorX(view1, 0, -90);
//                        ValueAnimator vAnim1 = ScaleAnimator.createAnimator(view1, view1.getHeight(),0 );
//                        AnimatorSet set1 = new AnimatorSet();
//                        set1.playTogether(objAnim1, vAnim1);
//                        set1.start();
//
//                        View view2 = mAppListView.getChildAt(2);
//                        view2 = mMyLin.getChildAt(2);
//                        view2.setPivotY(0);
//                        view2.setPivotX(view2.getWidth()/2);
//                        ObjectAnimator objAnim2 = MyRotateAnimator.createAnimatorX(view2, 0, -90);
//                        ValueAnimator vAnim2 = ScaleAnimator.createAnimator(view2, view2.getHeight(),0 );
//                        AnimatorSet set2 = new AnimatorSet();
//                        set2.playTogether(objAnim2, vAnim2);
//                        set2.start();
//
//                    } else {
//
//                        ScaleAnimator.collapse(mFold);
//                    }
//
//                    sCurrentVisibleFold = null;
////                    collapseFold(mFold);
////                        collapse(mFold);
////                        collapse(mFold);
////                        animateFold(mFold, 1000);
//                }
//
//            }
//        });
//    }

    public static class ViewAppType extends RelativeLayout {
        public ImageView mImg;
        public TextView mTitle;

        public ViewAppType(Context context) {
            super(context);
            StResol res = StResol.getInstance(context);

            mImg = res.mUI.mImg.img(context, null, res.id++);
            mTitle = res.mUI.mTv.textInit(context, 36, Color.BLACK, null, res.id++, "");

            addView(mImg, res.mUI.mLayout.relParam(100, 100, null, new int[]{10, 10, 10, 10}));
            addView(mTitle, res.mUI.mLayout.relParam(-2, -2, new int[]{RIGHT_OF, mImg.getId(), CENTER_VERTICAL}));
        }

        public void setData(ModelAppType data) {
            mImg.setImageResource(data.resImg);
            mTitle.setText(data.title);
            setBackgroundColor(data.colorBg);
        }
    }

    public static class AppListView extends ListView {
        public List<String> list;

        public AppListView(Context context) {
            super(context);
//            setOrientation(LinearLayout.VERTICAL);
            setBackgroundColor(Color.WHITE);
            List<String> list = Arrays.asList(new String[]{"One", "Two", "Three"});
            setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list));
        }
    }
}