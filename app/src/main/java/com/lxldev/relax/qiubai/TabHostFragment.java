package com.lxldev.relax.qiubai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxldev.relax.R;

public class TabHostFragment extends Fragment {

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private String TAG = getClass().getSimpleName();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {



        View view =  inflater.inflate(R.layout.fragment_tabs,container,false);

        this.initView(view);

        return view;
    }

    private void initView(View rootView){



        tabLayout = rootView.findViewById(R.id.common_tabs_tablayout);
        viewPager = rootView.findViewById(R.id.common_tabs_viewpager);


        viewPager.setAdapter(new TabAdapter(getChildFragmentManager()));

        tabLayout.setupWithViewPager(viewPager,true);


    }
}
