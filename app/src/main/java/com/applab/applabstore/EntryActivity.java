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
 *
 * Start from this Activity.
 * Display buttons to choose different com.applab.applabstore.activity(MyActivity) behavior.
 */
public class EntryActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        String[] buttonName = getResources().getStringArray(R.array.entry_buttons);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Arrays.asList(buttonName)));

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
    }
}
