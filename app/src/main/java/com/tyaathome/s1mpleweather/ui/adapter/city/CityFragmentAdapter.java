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

public class CityFragmentAdapter extends FragmentStatePagerAdapter {

    private List<String> dataList = new ArrayList<>();
    private SparseArray<Fragment> fragmentList = new SparseArray<>();

    public CityFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", dataList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentList.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public Fragment getRegisteredFragment(int position) {
        return fragmentList.get(position);
    }

    public void setData(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

}
