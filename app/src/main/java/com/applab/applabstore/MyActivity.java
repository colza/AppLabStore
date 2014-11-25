package com.applab.applabstore;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
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

import com.ptr.folding.BaseFoldingLayout;
import com.ptr.folding.FoldingLayout;
import com.ptr.folding.listener.OnFoldListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.herxun.library.singleton.StResol;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class MyActivity extends Activity{

    StResol stR;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stR = StResol.getInstance(this);

        ListView listView = new ListView(this);
        listView.setAdapter(new MyAdapter());

        List<MyModel> list = new ArrayList<MyModel>();
        for( int i = 0 ; i < 1 ; i++){
            list.add(new MyModel(R.drawable.ic_launcher, "Number " + i));
        }

        ((MyAdapter)listView.getAdapter()).applyDataToList(list);
        setContentView(listView);
    }

    public class MyModel{
        public int imgResource;
        public String Name;

        public MyModel(int res, String name){
            imgResource = res;
            Name = name;
        }
    }

    public class MyAdapter extends BaseAdapter{
        public List<MyModel> list;

        public MyAdapter(){
            list = new ArrayList<MyModel>();
        }

        public void applyDataToList(List<MyModel> dataToApply){
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
            if( view == null ){
                appView = new AppType(viewGroup.getContext());
            }
            else{
                appView = (AppType) view;
            }

            appView.setData((MyModel)getItem(i));
            return appView;

        }
    }

    public class AppType extends RelativeLayout{
        TextView mTv;
        ImageView mImg;
        FoldingLayout mFold;

        public AppType(Context context) {
            super(context);
            mTv = stR.mUI.mTv.textInit(context, 20, Color.BLUE, null, stR.id++, "App Type1");
            mImg = stR.mUI.mImg.img(context, R.drawable.ic_launcher, stR.id++);

            mImg.setImageResource(R.drawable.ic_launcher);
            mImg.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateFold(mFold,2000);
                }
            });

            addView(mImg);
            addView(mTv, stR.mUI.mLayout.relParam(-2, -2, new int[]{RIGHT_OF, mImg.getId()}));

            mFold = new FoldingLayout(context);
//            ImageView childView = new ImageView(context);
//            childView.setImageResource(R.drawable.ic_launcher);
//            childView.setBackgroundColor(Color.GREEN);

            mFold.addView(new MyListView(context));
            mFold.setBackgroundColor(Color.GREEN);

            addView(mFold, stR.mUI.mLayout.relParam(-1, 300, new int[]{BELOW, mImg.getId()}));

            mFold.setOrientation(BaseFoldingLayout.Orientation.VERTICAL);
            mFold.setFoldListener(new OnFoldListener() {
                @Override
                public void onStartFold() {
//                    mFold.setVisibility(View.VISIBLE);
                }

                @Override
                public void onEndFold() {
                    mFold.setVisibility(View.GONE);
                }
            });
        }

        public void setData(MyModel model){
            mImg.setImageResource(model.imgResource);
            mTv.setText(model.Name);
        }
    }

    public class MyListView extends ListView{

        public MyListView(Context context) {
            super(context);

            ArrayList<String> list = new ArrayList<String>();
            list.addAll(Arrays.asList(new String[]{"One","Two","Three","Four","One","Two","Three","Four","One","Two","Three","Four"}));
            setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list));
        }
    }


    /**
     * Animates the folding view inwards (to a completely folded state) from its
     * current state and then back out to its original state.
     */
    public void animateFold(final FoldingLayout fold, int duration) {
        float foldFactor = fold.getFoldFactor();

        ObjectAnimator animator = ObjectAnimator.ofFloat(fold,
                "foldFactor", foldFactor, 1);
//        animator.setRepeatMode(ValueAnimator.REVERSE);
//        animator.setRepeatCount(1);
        animator.setDuration(duration);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fold.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
