package com.tyaathome.s1mpleweather.ui.adapter.city;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.tyaathome.s1mpleweather.ui.fragment.CityFragment;

import java.util.ArrayList;
import java.util.List;

public class CityPagerFragmentAdapter extends FragmentStatePagerAdapter {

    private List<PagerFragmentHolder> holderList = new ArrayList<>();
    private List<PagerFragmentHolder> recycleHolderList = new ArrayList<>();
    private FragmentManager fragmentManager;

    public CityPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("CityPagerAdapter getItem:" + position + "\n");
        return new CityFragment();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        System.out.println("CityPagerAdapter instantiateItem:" + position + "\n");
        PagerFragmentHolder holder;
        if(recycleHolderList != null && recycleHolderList.size() > 0) {
            holder = recycleHolderList.get(0);
            holder.mPosition = POSITION_UNCHANGED;
        } else {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            holder = new PagerFragmentHolder(fragment);
            holderList.add(holder);
        }
        holder.mPosition = position;
        return holder.fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("CityPagerAdapter destroyItem:" + position + "\n");
        Fragment fragment = (Fragment) object;
        PagerFragmentHolder holder = new PagerFragmentHolder(fragment);
        holder.isRecycled = true;
        holder.mPosition = POSITION_NONE;
        recycleHolderList.add(holder);
        //super.destroyItem(container, position, fragment);
    }

    @Override
    public int getItemPosition(Object object) {
        int position = POSITION_UNCHANGED;
        if (object != null) {
            Fragment fragment = (Fragment) object;
            for(PagerFragmentHolder holder : holderList) {
                if(holder != null && holder.fragment == fragment) {
                    position = holder.mPosition;
                    position = position >= getCount() ? POSITION_NONE : position;
                }
            }
        }
        return position;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for(PagerFragmentHolder holder : holderList) {
            if(!holder.isRecycled && holder.mPosition < getCount()) {
                // TODO: 2018-4-24 update
            }
        }
    }

    public static class PagerFragmentHolder {
        final Fragment fragment;
        int mPosition = POSITION_UNCHANGED;
        boolean isRecycled = false;

        PagerFragmentHolder(Fragment fragment) {
            this.fragment = fragment;
        }
    }
}
