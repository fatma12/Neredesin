package com.ucinbir.xplugin.neredesin;

import android.os.Bundle;

import com.ucinbir.xplugin.neredesin.fragment.AyarlarFragment;

/**
 * Created by xplugin on 01.04.2017.
 */
public class AyarlarActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new AyarlarFragment()).commit();
    }

}
