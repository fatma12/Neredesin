package com.ucinbir.xplugin.neredesin.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ucinbir.xplugin.neredesin.fragment.HaritaFragment;
import com.ucinbir.xplugin.neredesin.fragment.KimlerFragment;
import com.ucinbir.xplugin.neredesin.fragment.ProfilFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new KimlerFragment();
            case 1:
                return new HaritaFragment();
            case 2:
                return new ProfilFragment();
        }

        throw new IllegalArgumentException("bilinmeyen tab fragment");

    }

    @Override
    public int getCount() {
        return 3;
    }
}
