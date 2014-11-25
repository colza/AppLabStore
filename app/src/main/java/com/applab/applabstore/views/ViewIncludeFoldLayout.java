package com.applab.applabstore.views;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applab.applabstore.Models.ModelAppType;
import com.applab.applabstore.MyActivity;
import com.applab.applabstore.R;
import com.applab.applabstore.adapter.AdapterAppType;
import com.applab.applabstore.animator.FoldingAnimator;
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
    private static FoldingLayout sCurrentVisibleFold;

    public ViewAppType mHeadView;
    public FoldingLayout mFold;
    private AppListView mAppListView;
    private StResol mRel;

    public ViewIncludeFoldLayout(Context context) {
        super(context);
        mRel = StResol.getInstance(context);
        setOrientation(VERTICAL);

        mHeadView = new ViewAppType(context);
        mHeadView.setId(mRel.id++);

        mFold = new FoldingLayout(context);
        mAppListView = new AppListView(context);

//        ImageView view = new ImageView(context);
//        view.setImageResource(R.drawable.ic_launcher);
//        view.setBackgroundColor(Color.BLUE);
//        mFold.addView(view);
        mFold.setOrientation(BaseFoldingLayout.Orientation.VERTICAL);
        mFold.addView(mAppListView);

        addView(mHeadView);

        mFold.setVisibility(View.GONE);
        addView(mFold, mRel.mUI.mLayout.linParam(-1, 0));
    }

    public void setData(ModelAppType data) {
        mHeadView.setData(data);
//        mAppListView.refreshList(data.appListStr);

        mHeadView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                int count = mAppListView.getAdapter().getCount();
                int height = StResol.getInstance(view.getContext()).mResolK.szPDtoPC(100) * count;

                if (mFold.getVisibility() == View.GONE) {
                    Log.i("LOG", "expand");

                    if (MyActivity.sIsFoldingEffect) {
                        mFold.getLayoutParams().height = height;
                        FoldingAnimator.expandFold(mFold);

                    } else {

                        if (sCurrentVisibleFold != null && sCurrentVisibleFold != mFold) {
                            ScaleAnimator.collapse(sCurrentVisibleFold);
                        }

                        ScaleAnimator.expand(mFold, height);
                    }

                    sCurrentVisibleFold = mFold;
//                        collapseFold(mFold);
//                        expand(mFold);
//                    expandFold(mFold);
//                        animateFold(mFold, 1000);
                } else {
                    Log.i("LOG", "collapse");
                    if (MyActivity.sIsFoldingEffect) {
                        FoldingAnimator.collapseFold(mFold);
                    } else {
                        ScaleAnimator.collapse(mFold);
                    }

                    sCurrentVisibleFold = null;
//                    collapseFold(mFold);
//                        collapse(mFold);
//                        collapse(mFold);
//                        animateFold(mFold, 1000);
                }

            }
        });
    }

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
            List<String> list = Arrays.asList(new String[]{"One", "Two", "Three"});
            setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list));
        }
    }
}