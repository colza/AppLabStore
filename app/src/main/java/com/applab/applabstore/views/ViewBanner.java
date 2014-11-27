package com.applab.applabstore.views;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.herxun.library.singleton.StResol;

/**
 * Created by TsaiKunYu on 26/11/2014.
 */
public class ViewBanner extends RelativeLayout {
    StResol stResl;
    private ImageView img;
    private TextView tv;

    public ViewBanner(Context context) {
        super(context);
        stResl = StResol.getInstance(context);

        img = stResl.mUI.mImg.img(context, null, stResl.id++);
        tv = stResl.mUI.mTv.textInit(context,40, Color.BLACK, null,stResl.id++,"");

        int pad = stResl.mResolK.szPDtoPC(50);
        setPadding(0,pad,0,pad);

        addView(img, stResl.mUI.mLayout.relParam(150,150,new int[]{CENTER_HORIZONTAL}));
        addView(tv, stResl.mUI.mLayout.relParam(-2,-2, new int[]{BELOW, img.getId(), CENTER_HORIZONTAL}, new int[]{0,20,0,0}));
    }

    public void setData(int bgColor, int imgRes, String title){
        setBackgroundColor(bgColor);
        img.setImageResource(imgRes);
        tv.setText(title);
    }
}
