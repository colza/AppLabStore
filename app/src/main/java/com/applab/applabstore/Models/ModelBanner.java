package com.applab.applabstore.Models;

import android.os.Bundle;

/**
 * Created by TsaiKunYu on 26/11/2014.
 */
public class ModelBanner{
    public int resImg;
    public String title;
    public int bgColor;

    public static final String keyImg = "keyImg";
    public static final String keyTitle = "keyTitle";
    public static final String keyColor = "keyColor";

    public static Bundle transform(ModelBanner model){
        Bundle b = new Bundle();
        b.putInt(keyImg, model.resImg);
        b.putInt(keyColor, model.bgColor);
        b.putString(keyTitle, model.title);
    }

    public static ModelBanner transform(Bundle b){
        ModelBanner model = new ModelBanner();
        model.resImg = b.getInt(keyImg);
        model.bgColor = b.getInt(keyColor);
        model.title = b.getString(keyTitle);

    }
}
