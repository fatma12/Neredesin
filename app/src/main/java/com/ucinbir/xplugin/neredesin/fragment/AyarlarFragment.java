package com.ucinbir.xplugin.neredesin.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ucinbir.xplugin.neredesin.R;

/**
 * Created by xplugin on 01.04.2017.
 */
public class AyarlarFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
