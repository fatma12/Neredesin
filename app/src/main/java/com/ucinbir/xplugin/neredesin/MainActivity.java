package com.ucinbir.xplugin.neredesin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.ucinbir.xplugin.neredesin.adapter.KimlerRecyclerViewAdapter;
import com.ucinbir.xplugin.neredesin.adapter.MainPagerAdapter;


public class MainActivity extends BaseActivity implements ActionBar.TabListener, KimlerRecyclerViewAdapter.KimlerItemClickListener {

    private MainPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final String[] tabTitles = getResources().getStringArray(R.array.tab_title_array);

        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                actionBar.setTitle(tabTitles[position]);
            }

        });

        actionBar.addTab(actionBar.newTab().setTabListener(this)
                .setIcon(R.drawable.ic_kimler));
        actionBar.addTab(actionBar.newTab().setTabListener(this)
                .setIcon(R.drawable.ic_harita));
        actionBar.addTab(actionBar.newTab().setTabListener(this)
                .setIcon(R.drawable.ic_profil));

        String yakinArkadasKullaniciAdi = getYakinArkadasKullaniciAdi();

        if (!TextUtils.isEmpty(yakinArkadasKullaniciAdi)) {
            haritaEkraninaGec();
            yakinArkadasBroadcastGonder(yakinArkadasKullaniciAdi);
        }

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_ayarlar:
                Intent intent = new Intent(this, AyarlarActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private String getYakinArkadasKullaniciAdi() {
        Intent intent = getIntent();

        if(intent.getExtras() != null && intent.getExtras().getString(BaseActivity.ARKADAS_INTENT_EXTRA) != null)
            return intent.getExtras().getString(BaseActivity.ARKADAS_INTENT_EXTRA);

        return null;
    }

    private void haritaEkraninaGec() {
        viewPager.setCurrentItem(1);
    }

    private void yakinArkadasBroadcastGonder(String kullaniciAdi) {
        Intent intent = new Intent(BaseActivity.YAKIN_ARKADAS_BROADCAST);
        intent.putExtra(BaseActivity.ARKADAS_INTENT_EXTRA, kullaniciAdi);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onItemClick(String kullaniciAdi) {
        haritaEkraninaGec();
        yakinArkadasBroadcastGonder(kullaniciAdi);
    }

}
