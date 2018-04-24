package com.tyaathome.s1mpleweather.ui.adapter.city;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.ui.fragment.CityFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> dataList = new ArrayList<>();
    private SparseArray<Fragment> fragmentList = new SparseArray<>();
    private int currentPageIndex;

    public FragmentAdapter(FragmentManager fm, List<String> dataList) {
        super(fm);
        this.dataList = dataList;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("index", dataList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragmentList.get(position);
        if(fragment == null) {
            fragment = (Fragment) super.instantiateItem(container, position);
        }
        fragmentList.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        if(fragmentList.size() > currentPageIndex && fragmentList.get(currentPageIndex) == object) {
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
//        return POSITION_NONE;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void setCurrentPage(int index) {
        currentPageIndex = index;
    }
}
