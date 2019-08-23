package com.app.materialwallpaper.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.materialwallpaper.Config;
import com.app.materialwallpaper.R;
import com.app.materialwallpaper.activities.MainActivity;
import com.app.materialwallpaper.utilities.AppBarLayoutBehavior;

public class FragmentExplore extends Fragment {

    private MainActivity mainActivity;
    private Toolbar toolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    public static int tab_count = 5;

    public FragmentExplore() {

    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_layout, container, false);

        AppBarLayout appBarLayout = view.findViewById(R.id.tab_appbar_layout);
        ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams()).setBehavior(new AppBarLayoutBehavior());

        tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        viewPager = view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(tab_count);

        toolbar = view.findViewById(R.id.toolbar);
        setupToolbar();

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(1);
            }
        });

        if (Config.ENABLE_RTL_MODE) {
            viewPager.setRotationY(180);
        }

        return view;

    }

    public class MyAdapter extends FragmentPagerAdapter {

        private MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new FragmentCategory();
                case 1:
                    return new FragmentRecent();
                case 2:
                    return new FragmentFeatured();
                case 3:
                    return new FragmentPopular();
                case 4:
                    return new FragmentRandom();

            }

            return null;
        }

        @Override
        public int getCount() {
            return tab_count;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.tab_category);
                case 1:
                    return getResources().getString(R.string.tab_recent);
                case 2:
                    return getResources().getString(R.string.tab_featured);
                case 3:
                    return getResources().getString(R.string.tab_popular);
                case 4:
                    return getResources().getString(R.string.tab_shuffle);
            }
            return null;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupNavigationDrawer(toolbar);
    }

    private void setupToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
        mainActivity.setSupportActionBar(toolbar);
    }

}

