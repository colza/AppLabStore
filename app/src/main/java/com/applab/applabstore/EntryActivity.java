package com.applab.applabstore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

import co.herxun.library.singleton.StResol;

/**
 * Created by TsaiKunYu on 26/11/2014.
 */
public class EntryActivity extends Activity{

    public StResol stResol;
    private TextView scaleExpand;
    private TextView foldExpand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stResol = StResol.getInstance(this);

        ListView listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"Scale Expand/Collapse", "Fold Expand/Collapse"})));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(EntryActivity.this, MyActivity.class);
                switch(i){
                    case 0:
                        it.putExtra(MyConstants.ExpandType, MyConstants.ScaleExpand);
                        break;
                    case 1:
                        it.putExtra(MyConstants.ExpandType, MyConstants.FoldExpand);
                        break;
                }
                startActivity(it);
            }
        });

        setContentView(listView);
//
//        RelativeLayout rel = new RelativeLayout(this);
//
//        scaleExpand = stResol.mUI.mTv.textInit(this, 50, Color.BLACK, null, stResol.id++, "Scale Expand/Collapse");
//        foldExpand = stResol.mUI.mTv.textInit(this, 50, Color.BLACK, null, stResol.id++, "Fold Expand/Collapse");
//
//        rel.addView(scaleExpand, stResol.mUI.mLayout.relParam(-2,-2, null));
//        rel.addView(foldExpand, stResol.mUI.mLayout.relParam(-2,-2, new int[]{ RelativeLayout.BELOW, scaleExpand.getId()}));
//
//        int pad = stResol.mResolK.szPDtoPC(30);
//        scaleExpand.setPadding(pad,pad,pad,pad);
//        foldExpand.setPadding(pad,pad,pad,pad);
//
////        scaleExpand.setBackgroundResource(R.drawable.btnselector);
////        foldExpand.setBackgroundResource(R.drawable.btnselector);
//
//        setContentView(rel);
//
//        scaleExpand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(EntryActivity.this, MyActivity.class);
//                it.putExtra(MyConstants.ExpandType, MyConstants.ScaleExpand);
//                startActivity(it);
//            }
//        });
//
//        foldExpand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(EntryActivity.this, MyActivity.class);
//                it.putExtra(MyConstants.ExpandType, MyConstants.FoldExpand);
//                startActivity(it);
//            }
//        });
    }

    private class CompoundButton extends LinearLayout{
        ImageView img;
        TextView text;
        public CompoundButton(Context context) {
            super(context);

            text = stResol.mUI.mTv.textInit(context, 30, Color.BLACK, null, stResol.id++, "");
            text.setGravity(Gravity.CENTER);

            addView(text);
        }
    }
}
