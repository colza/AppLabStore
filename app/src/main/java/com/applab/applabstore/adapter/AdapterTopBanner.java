package com.applab.applabstore.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.applab.applabstore.Models.ModelBanner;
import com.applab.applabstore.fragments.BannerFragment;

import java.util.List;

/**
 * Created by TsaiKunYu on 26/11/2014.
 */
public class AdapterTopBanner extends FragmentPagerAdapter{
    private List<ModelBanner> list;

    public AdapterTopBanner(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int i) {
        return BannerFragment.newInstance(i, list.get(i));
    }

    @Override
    public int getCount() {
        return 0;
    }
}
