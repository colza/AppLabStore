package com.applab.applabstore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.applab.applabstore.Models.ModelAppType;
import com.applab.applabstore.adapter.AdapterAppType;
import com.applab.applabstore.animator.MyRotateAnimator;
import com.applab.applabstore.views.ViewIncludeFoldLayout;
import com.ptr.folding.BaseFoldingLayout;
import com.ptr.folding.FoldingLayout;
import com.ptr.folding.listener.OnFoldListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.herxun.library.singleton.StResol;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MyActivity extends Activity {
    public static Boolean sIsFoldingEffect = false;
    StResol stR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stR = StResol.getInstance(this);

        ListView listView = new ListView(this);
        AdapterAppType ada = new AdapterAppType();
        listView.setAdapter(ada);

        List<ModelAppType> listData = new ArrayList<ModelAppType>();
        for (int i = 0; i < 4; i++) {
            ModelAppType data = new ModelAppType();
            data.resImg = R.drawable.ic_launcher;
            data.title = "Hello";
            data.colorBg = Color.GRAY;
            data.appListStr = Arrays.asList(new String[]{"One", "Two", "Three"});
            listData.add(data);
        }

        ada.addListItem(listData);
        ada.notifyDataSetChanged();
        setContentView(listView);

//        LinearLayout lin = new LinearLayout(this);
//        lin.setOrientation(LinearLayout.VERTICAL);
//
//        for(int i = 0 ; i < 4 ; i++ ){
//            ModelAppType data = new ModelAppType();
//            data.resImg = R.drawable.ic_launcher;
//            data.title = "Hello";
//            data.colorBg = Color.GRAY;
//            data.appListStr = Arrays.asList(new String[]{"One","Two","Three"});
//            ViewIncludeFoldLayout view = new ViewIncludeFoldLayout(this);
//            view.setData(data);
//            lin.addView(view);
//        }
//
//        setContentView(lin);

//        listView.setAdapter(new MyAdapter());
//
//        List<MyModel> list = new ArrayList<MyModel>();
//        for (int i = 0; i < 1; i++) {
//            list.add(new MyModel(R.drawable.ic_launcher, "Number " + i));
//        }
//
//        ((MyAdapter) listView.getAdapter()).applyDataToList(list);
//        setContentView(new AppType(this));

//        ViewIncludeFoldLayout view = new ViewIncludeFoldLayout(this);
//        ModelAppType data = new ModelAppType();
//        data.resImg = R.drawable.ic_launcher;
//        data.title = "Hello";
//        data.colorBg = Color.GRAY;
//        data.appListStr = Arrays.asList(new String[]{"One", "Two", "Three"});
//        view.setData(data);
//        setContentView(view);
    }


    public class MyModel {
        public int imgResource;
        public String Name;

        public MyModel(int res, String name) {
            imgResource = res;
            Name = name;
        }
    }

    public class MyAdapter extends BaseAdapter {
        public List<MyModel> list;

        public MyAdapter() {
            list = new ArrayList<MyModel>();
        }

        public void applyDataToList(List<MyModel> dataToApply) {
            list.addAll(dataToApply);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            AppType appView;
            if (view == null) {
                appView = new AppType(viewGroup.getContext());
            } else {
                appView = (AppType) view;
            }

            appView.setData((MyModel) getItem(i));
            return appView;

        }
    }

    public class MyFoldingLayout extends FoldingLayout {
        public MyFoldingLayout(Context context) {
            super(context);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
        }
    }

//    public ImageView mBottomImg;

    public class AppType extends RelativeLayout {
        TextView mTitle;
        ImageView mImgOnLeft;
        MyFoldingLayout mFold;
        LinearLayout secView;
        LinearLayout thirdView;


        public AppType(Context context) {
            super(context);
            mTitle = stR.mUI.mTv.textInit(context, 40, Color.BLACK, null, stR.id++, "App Type1");
            mImgOnLeft = stR.mUI.mImg.img(context, R.drawable.ic_launcher, stR.id++);
            mImgOnLeft.setBackgroundColor(Color.GRAY);

            RelativeLayout rel = new RelativeLayout(context);
            rel.setId(stR.id++);

            rel.addView(mImgOnLeft, stR.mUI.mLayout.relParam(100, 100, null, new int[]{20, 20, 20, 20}));
            rel.addView(mTitle, stR.mUI.mLayout.relParam(-2, -2, new int[]{RIGHT_OF, mImgOnLeft.getId(), CENTER_VERTICAL}));


//            mImgOnLeft.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    collapseFold2(mFold);
//                }
//            });

            mTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mFold.setPivotX(0);
//                    mFold.animate().rotationXBy(90).rotationX(0).scaleY(1.5f);

                    mFold.setPivotY(0);
                    mFold.setPivotX(mFold.getWidth()/2);
                    ObjectAnimator anim = MyRotateAnimator.createAnimatorX(mFold, 0, -90);
                    ValueAnimator anim2 = createAnimator(mFold, mFold.getHeight(), 0);
                    anim.setInterpolator(new AccelerateInterpolator());
                    anim.setDuration(3000);
                    anim2.setDuration(3000);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(anim, anim2);
                    set.start();

//                    secView.setPivotX(secView.getHeight());
                    int h = secView.getHeight();
                    Log.i("LOG", "height = " + h);
//                    secView.setPivotY(secView.getHeight());
                    secView.setPivotY(h);
                    secView.setPivotX(secView.getWidth()/2);
                    ObjectAnimator animSec = MyRotateAnimator.createAnimatorX(secView, 0, 90);
                    ValueAnimator animSec2 = createAnimator(secView, secView.getHeight(), 0);
                    animSec2.setInterpolator(new AccelerateInterpolator());
                    animSec.setDuration(3000);
                    animSec2.setDuration(3000);
                    AnimatorSet set2 = new AnimatorSet();
                    set2.playTogether(animSec, animSec2);
                    set2.start();
                }
            });
//
//            rel.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    ObjectAnimator anim = MyRotateAnimator.createAnimatorX(mFold, 0, -90 );
//                    anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                            float value = (Float) valueAnimator.getAnimatedValue();
//                            Object res = valueAnimator.getAnimatedValue("pivotX");
//                            if( res != null ){
//                                float alpha = (Float)res;
//                                Log.i("LOG","alpha = " + alpha);
//                            }
//                            else{
//                                Log.i("LOG","alpha = NULL");
//                            }
//
//                            // this is the angle
//                            Log.i("LOG","Anim value = " + value);
//                        }
//                    });
//
//                    ValueAnimator anim1 = createAnimator(mFold, mFold.getHeight(), 0);
//                    // where trigger animation
//                    if (mFold.getVisibility() == View.GONE) {
//                        Log.i("LOG", "expand");
////                        collapseFold(mFold);
////                        expand(mFold);
////                        expandFold(mFold);
////                        expand(mFakeBgView);
////                        animateFold(mFold, 1000);
//
//                        anim.start();
////                        anim1.start();
//
//                    } else {
//                        Log.i("LOG", "collapse");
//
//                        anim.start();
////                        anim1.start();
////                        collapseFold(mFold);
////                        collapse(mFakeBgView);
////                        collapse(mFold);
////                        collapse(mFold);
////                        animateFold(mFold, 1000);
//                    }
//                }
//            });

            addView(rel, stR.mUI.mLayout.relParam(-1, -2, null));

            mFold = new MyFoldingLayout(context);
            mFold.setOrientation(BaseFoldingLayout.Orientation.VERTICAL);
            mFold.setId(stR.id++);

            LinearLayout lin = new LinearLayout(context);
            lin.setOrientation(LinearLayout.VERTICAL);

            ImageView childView = new ImageView(context);
            childView.setImageResource(R.drawable.ic_launcher);
            childView.setBackgroundColor(Color.GREEN);
            lin.addView(childView, stR.mUI.mLayout.linParam(100,100));

            TextView ch2 = new TextView(context);
            ch2.setText("Hello\n My name is Quentin \n I'm an Android Developer \n who loves creating customized effect!!");
            lin.addView(ch2);

//            mFold.addView(new MyListView(context));
            mFold.addView(lin);
            mFold.setId(stR.id++);
//            mFold.setBackgroundColor(Color.YELLOW);
            addView(mFold, stR.mUI.mLayout.relParam(-1, 300, new int[]{BELOW, rel.getId()}));
            mFold.setBackgroundResource(R.drawable.border4_thumbs);
//            mFold.setBackgroundColor(Color.GREEN);

//            mFakeBgView = new View(context);
//            mFakeBgView.setId(stR.id++);
//            mFakeBgView.setBackgroundColor(Color.BLACK);
//            addView(mFakeBgView, stR.mUI.mLayout.relParam(-1, 300, new int[]{BELOW, rel.getId()}));

            secView = new LinearLayout(context);
            secView.setId(stR.id++);
            secView.setBackgroundResource(R.drawable.border4_thumbs);
//            secView.setBackgroundColor(Color.BLUE);
            secView.setOrientation(LinearLayout.VERTICAL);

            ImageView img = new ImageView(context);
            img.setImageResource(R.drawable.ic_launcher);
//            img.setBackgroundColor(Color.GREEN);
            secView.addView(img, stR.mUI.mLayout.linParam(100,100));

            TextView ch3 = new TextView(context);
            ch3.setText("Hello\n My name is Quentin \n I'm an Android Developer \n who loves creating customized effect!!");
            ch3.setTextSize(20);
            secView.addView(ch3);
            addView(secView, stR.mUI.mLayout.relParam(-1, -2, new int[]{BELOW, mFold.getId()}));

            thirdView = new LinearLayout(context);
            thirdView.setBackgroundColor(Color.MAGENTA);
            thirdView.setOrientation(LinearLayout.VERTICAL);
            TextView ch4 = new TextView(context);
            ch4.setTextSize(20);
            ch4.setText("Hello\n My name is Quentin \n I'm an Android Developer \n who loves creating customized effect!!");
            thirdView.addView(ch4);

            addView(thirdView, stR.mUI.mLayout.relParam(-1, -2, new int[]{BELOW, secView.getId()}));
        }

        public void setData(MyModel model) {
//            mImgOnLeft.setImageResource(model.imgResource);
            mTitle.setText(model.Name);
        }
    }

    public class MyListView extends ListView {

        public MyListView(Context context) {
            super(context);

            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(new String[]{"One", "Two", "Three", "Four", "One", "Two", "Three", "Four", "One", "Two", "Three", "Four"}));
            setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list));
        }
    }

    private final int FOLD_ANIMATION_DURATION = 1000;

    public void expandFold(final FoldingLayout rootView) {


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

    public void collapseFold2(final FoldingLayout rootView){
        ObjectAnimator animator = createFoldAnimator(rootView, rootView.getFoldFactor(), 1.0f);
        animator.start();
//        ValueAnimator anim = createAnimator(rootView, rootView.getHeight(), 0);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animator, anim);
//        set.start();
    }

    public void collapseFold(final FoldingLayout rootView) {
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

    int count = 0;

    public ObjectAnimator createFoldAnimator(final View rootView, float start, float end) {
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

    public void expand(final View rootView) {
        rootView.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        rootView.measure(widthSpec, heightSpec);
        ValueAnimator anim = createAnimator(rootView, 0, originHeight);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.setVisibility(View.GONE);
            }
        });
        anim.start();
    }

    int originHeight = 0;

    public void collapse(final View rootView) {
        if (originHeight == 0) {
            originHeight = rootView.getHeight();
        }

        ValueAnimator anim = createAnimator(rootView, originHeight, 0);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                rootView.setVisibility(View.GONE);
            }
        });

        anim.start();
    }

    public ValueAnimator createAnimator(final View rootView, int start, int end) {
        ValueAnimator anim = ValueAnimator.ofInt(start, end);
        anim.setDuration(FOLD_ANIMATION_DURATION);
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

//    public ValueAnimator createAnimator(final AppType rootView, float start, float end){
//        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                float value = (Float) valueAnimator.getAnimatedValue();
//                rootView.getLayoutParams().height =
//            }
//        });
//        float foldFactor = fold.getFoldFactor();
//
////        ObjectAnimator animator = ObjectAnimator.ofFloat(fold,
////                "foldFactor", foldFactor, 1);
//        ObjectAnimator animator = ObjectAnimator.ofFloat(fold,
//                "foldFactor", foldFactor, 0.5f);
////        animator.setRepeatMode(ValueAnimator.REVERSE);
////        animator.setRepeatCount(1);
//        ObjectAnimator.setFrameDelay(1000);
//        animator.setDuration(duration);
//        animator.setInterpolator(new AccelerateInterpolator());
//        final int height = fold.getHeight();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                Float f = (Float) valueAnimator.getAnimatedValue();
//
//                Log.i("update", ((Float) valueAnimator.getAnimatedValue()).toString());
////                Log.i("update", "ScaleY = " + fold.getScaleY() + ", Y = "+ fold.getY() + ", Bot = " + fold.getBottom());
//                Log.i("update","RotationX = " + fold.getRotationX() + ", RotationY = " + fold.getRotationY());
//                Log.i("update","PivotX = " + fold.getPivotX() + ", PivotY = " + fold.getPivotY());
//                Log.i("update","height = " + fold.getHeight());
//
//                Float result = 1.0f-f;
//                fold.getLayoutParams().height = (int) (height*result);
//                fold.requestLayout();
//            }
//        });
//    }

    /**
     * Animates the folding view inwards (to a completely folded state) from its
     * current state and then back out to its original state.
     */
    public void animateFold(final FoldingLayout fold, int duration) {
        float foldFactor = fold.getFoldFactor();
        Log.i("LOG", "foldFactor = " + foldFactor);

//        ObjectAnimator animator = ObjectAnimator.ofFloat(fold,
//                "foldFactor", foldFactor, 1);
        ObjectAnimator animator = ObjectAnimator.ofFloat(fold,
                "foldFactor", foldFactor, 0.5f);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(1);
//        ObjectAnimator.setFrameDelay(1000);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
//        final int height = fold.getHeight();
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                Float f = (Float) valueAnimator.getAnimatedValue();
//
//                Log.i("update", ((Float) valueAnimator.getAnimatedValue()).toString());
////                Log.i("update", "ScaleY = " + fold.getScaleY() + ", Y = "+ fold.getY() + ", Bot = " + fold.getBottom());
//                Log.i("update","RotationX = " + fold.getRotationX() + ", RotationY = " + fold.getRotationY());
//                Log.i("update","PivotX = " + fold.getPivotX() + ", PivotY = " + fold.getPivotY());
//                Log.i("update","height = " + fold.getHeight());
//
//                Float result = 1.0f-f;
//                fold.getLayoutParams().height = (int) (height*result);
//                fold.requestLayout();
//            }
//        });
//
//        animator.addListener(new AnimatorListenerAdapter(){
//        });
        animator.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.anim_effect) {
            sIsFoldingEffect = sIsFoldingEffect ? false : true;
            item.setTitle(sIsFoldingEffect ? R.string.effect_scale : R.string.effect_folding);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
