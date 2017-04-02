package com.ucinbir.xplugin.neredesin.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.ucinbir.xplugin.neredesin.BaseActivity;
import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.background.KonumSorgulaAsyncTask;

public class HaritaFragment extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private GoogleMap googleMap;
    private Location mevcutKonum;
    private String arkadasKullaniciAdi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_harita, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        init();
    }

    private void init() {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        googleMap = supportMapFragment.getMap();
        googleMap.setMyLocationEnabled(true);

        buildGoogleApiClient();
        createLocationRequest();
    }

    @Override
    public void onLocationChanged(Location location) {
        mevcutKonum = location;
        new KonumSorgulaAsyncTask(getActivity(), googleMap, location, arkadasKullaniciAdi).execute();
    }

    protected void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void createLocationRequest() {
        locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onResume() {
        super.onResume();
        googleApiClient.connect();
        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(yakinArkadasBroadcastReceiver, new IntentFilter(BaseActivity.YAKIN_ARKADAS_BROADCAST));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

        googleApiClient.disconnect();

        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(yakinArkadasBroadcastReceiver);

    }

    @Override
    public void onConnected(Bundle connectionHint) {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
    }

    private BroadcastReceiver yakinArkadasBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            arkadasKullaniciAdi = intent.getStringExtra(BaseActivity.ARKADAS_INTENT_EXTRA);

            if(mevcutKonum != null)
                new KonumSorgulaAsyncTask(getActivity(), googleMap, mevcutKonum, arkadasKullaniciAdi).execute();

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_harita, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.haritaActionYenileMenuItem:
                haritaYenile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void haritaYenile() {

        arkadasKullaniciAdi = null;

        if (mevcutKonum != null)
            new KonumSorgulaAsyncTask(getActivity(), googleMap, mevcutKonum, arkadasKullaniciAdi).execute();

    }

}
