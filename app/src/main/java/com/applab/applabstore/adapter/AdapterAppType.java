package com.applab.applabstore.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.applab.applabstore.Models.ModelAppType;
import com.applab.applabstore.views.ViewIncludeFoldLayout;
import com.ptr.folding.FoldingLayout;

import java.util.ArrayList;
import java.util.List;

import co.herxun.library.singleton.StResol;

/**
 * Created by TsaiKunYu on 25/11/2014.
 */
public class AdapterAppType extends BaseAdapter{

    private List<ModelAppType> list;
    public AdapterAppType(){
        list = new ArrayList<ModelAppType>();
    }

    public void addListItem(List<ModelAppType> fromList){
        list.addAll(fromList);
        notifyDataSetChanged();
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
        ViewIncludeFoldLayout v;
        if( view == null ){
            v = new ViewIncludeFoldLayout(viewGroup.getContext());
        }
        else{
            v = (ViewIncludeFoldLayout)view;
        }

        ModelAppType data = (ModelAppType) getItem(i);
        v.setData(data);

        return v;
    }


}
