package com.ucinbir.xplugin.neredesin.background;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.database.DatabaseManager;
import com.ucinbir.xplugin.neredesin.database.Profil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KonumSorgulaAsyncTask extends AsyncTask<Void, Void, List<Konum>>{

	private static final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private Context context;
	private GoogleMap googleMap;
	private Location location;
	private String arkadasKullaniciAdi;
	
	public KonumSorgulaAsyncTask(Context context, GoogleMap googleMap, Location location, String arkadasKullaniciAdi) {
		super();
		this.context = context;
		this.location = location;
		this.googleMap = googleMap;
		this.arkadasKullaniciAdi = arkadasKullaniciAdi;
	}

	@Override
	protected List<Konum> doInBackground(Void... params) {
		return getKonumList();
	}
	
	private List<Konum> getKonumList() {
		
		String kullaniciAdi = getKullaniciAdi();
		if(TextUtils.isEmpty(kullaniciAdi))
			return new ArrayList<>();
		
		
		return NetworkManager.konumSorgula(kullaniciAdi);
	}
	
	@Override
	protected void onPostExecute(List<Konum> arkadasKonumListesi) {
		googleMap.clear();
		
		if(arkadasKonumListesi == null || arkadasKonumListesi.size() == 0)
			return;

		mevcutKonumuHaritayaEkle(location);

		List<Konum> arkadasListesi = getGosterilecekArkadasList(arkadasKonumListesi);

        arkadaslariHaritayaEkle(arkadasListesi);
		haritayiOrtala(arkadasListesi, location);
		
	}

	private List<Konum> getGosterilecekArkadasList(List<Konum> arkadasKonumListesi) {

		String haritadaGosterilecekArkadas = getHaritadaGosterilecekArkadas();

		if(TextUtils.isEmpty(haritadaGosterilecekArkadas))
			return arkadasKonumListesi;

		List<Konum> arkadasListesi = new ArrayList<>();
		for (Konum konum : arkadasKonumListesi) {

			if (haritadaGosterilecekArkadas.equalsIgnoreCase(konum.getKullaniciAdi())) {
				arkadasListesi.add(konum);
				break;
			}

		}

		return arkadasListesi;
	}
	
	private void arkadaslariHaritayaEkle(List<Konum> arkadasKonumListesi) {

		for (Konum konum : arkadasKonumListesi) {
			LatLng latLng = new LatLng(konum.getEnlem(), konum.getBoylam());
			String guncellemeZamani = context.getResources().getString(R.string.son_guncelleme, format.format(konum.getGuncellemeZamani()));
			
			MarkerOptions markerOptions = new MarkerOptions()
					.position(latLng)
					.title(konum.getKullaniciAdi())
					.snippet(guncellemeZamani)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.ic_arkadas_konum));

			googleMap.addMarker(markerOptions);
		}
	}
	
	private void mevcutKonumuHaritayaEkle(Location location) {
		LatLng mevcutKonumLatLng = new LatLng(location.getLatitude(), location.getLongitude());
		googleMap.addMarker(new MarkerOptions()
        .position(mevcutKonumLatLng)
        .anchor(0.5f, 0.5f)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_mevcut_konum)));
	}
	
	private void haritayiOrtala(List<Konum> arkadasKonumListesi, Location location) {
		
		arkadasKonumListesi.add(new Konum(null, location.getLatitude(), location.getLongitude()));
		
		LatLngBounds.Builder builder = new LatLngBounds.Builder();

		for (Konum konum : arkadasKonumListesi) {
			builder.include(new LatLng(konum.getEnlem(), konum.getBoylam()));
		}
		
		LatLngBounds bounds = builder.build();
		
		googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 50));
	}

	private String getHaritadaGosterilecekArkadas() {
        return arkadasKullaniciAdi != null ? arkadasKullaniciAdi : null;
	}

	private String getKullaniciAdi() {
		
		DatabaseManager manager = new DatabaseManager(context);
		
		Profil profil = manager.profilSorgula(null);
		
		if(profil == null)
			return null;
		
		return profil.getKullaniciAdi();
	}
	
	
}
