package com.lxldev.relax.qiubai;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lxldev.relax.qiubai.tabs.hot.JokeHotListFragment;

public class TabAdapter extends FragmentPagerAdapter {


    private Fragment dataSource[] = new Fragment[]{new JokeHotListFragment()};

    private String titles[] = new String[]{"热门","热图","趣图","精选"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return dataSource[position];
    }

    @Override
    public int getCount() {
        return dataSource.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
