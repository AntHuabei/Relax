package com.lxldev.relax.news163;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lxldev.relax.news163.tabs.joke.JokeFragment;
import com.lxldev.relax.news163.tabs.topnews.TopNewsFragment;

public class TabAdapter extends FragmentPagerAdapter {


    private Fragment dataSource[] = new Fragment[]{new JokeFragment(),new TopNewsFragment()};

    private String titles[] = new String[]{"段子","新闻"};

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
