package com.applab.applabstore;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.applab.applabstore.Models.ModelAppType;
import com.applab.applabstore.adapter.AdapterAppType;
import java.util.ArrayList;
import java.util.List;

import co.herxun.library.singleton.StResol;

/**
 * Created by TsaiKunYu on 26/11/2014.
 * <p/>
 * This Activity containing the view which show the folding & expand/collapse effect.
 */
public class MyActivity extends Activity {
    StResol stR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stR = StResol.getInstance(this);

        MyConstants.sExpandType = getIntent().getExtras().getInt(MyConstants.ExpandType);

        ListView listView = new ListView(this);
        AdapterAppType ada = new AdapterAppType();
        listView.setAdapter(ada);

        TypedArray icons = getResources().obtainTypedArray(R.array.icons);
        TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        TypedArray titles = getResources().obtainTypedArray(R.array.title);

        List<ModelAppType> listData = new ArrayList<ModelAppType>();
        for (int i = 0; i < 5; i++) {
            ModelAppType data = new ModelAppType();
            data.resImg = icons.getResourceId(i, R.drawable.ic_launcher);
            data.title = titles.getString(i);
            data.colorBg = colors.getColor(i, 0xFF000000);
            data.index = i;
            listData.add(data);
        }

        ada.addListItem(listData);
        setContentView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
