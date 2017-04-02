package com.ucinbir.xplugin.neredesin.background;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class KonumGuncelleService extends Service implements
		GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {

	private GoogleApiClient googleApiClient;
	private LocationRequest locationRequest;
	
	@Override
	public void onCreate() {
		Log.d("KonumGuncelleService", "olusturuldu");
		super.onCreate();
		
		buildGoogleApiClient();
		createLocationRequest();
	}

	protected void buildGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API)
				.build();
	}

	protected void createLocationRequest() {
		locationRequest = LocationRequest.create()
				.setNumUpdates(1)
				.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("KonumGuncelleService", "baslatildi");
		
		if(!googleApiClient.isConnected())
			googleApiClient.connect();
		
		return START_NOT_STICKY;
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		LocationServices.FusedLocationApi.requestLocationUpdates(
				googleApiClient, locationRequest, this);
	}
	
	public void onLocationChanged(Location location) {
		new UzaklikHesaplaAsyncTask(this).execute(location);
		stopSelf();
	}

	@Override
	public void onConnectionSuspended(int cause) {
		googleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {

		if (googleApiClient.isConnected()) {
			LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
		}

		googleApiClient.disconnect();
		super.onDestroy();
	}

}
