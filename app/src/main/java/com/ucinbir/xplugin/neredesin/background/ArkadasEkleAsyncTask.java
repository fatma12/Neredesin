package com.ucinbir.xplugin.neredesin.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.BaseActivity;
import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.adapter.KimlerRecyclerViewAdapter;
import com.ucinbir.xplugin.neredesin.database.DatabaseManager;
import com.ucinbir.xplugin.neredesin.database.Profil;

import java.util.ArrayList;
import java.util.List;

public class ArkadasEkleAsyncTask extends AsyncTask<String, String, List<Profil>> {
	
	public static final String BASARILI = "1";
	public static final String ARKADAS_BULUNAMADI_ERROR = "-2";
	public static final String ARKADAS_ZATEN_MEVCUT_ERROR = "-3";
	public static final String PROFIL_BULUNAMADI_ERROR = "-4";
	private String sonucKodu;
	
	private FragmentActivity activity;
	private KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener;
	private ProgressDialog progressDialog;
	private RecyclerView recyclerView;
	
	public ArkadasEkleAsyncTask(FragmentActivity activity, KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener) {
		super();
		this.activity = activity;
		this.kimlerItemClickListener = kimlerItemClickListener;
		this.recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
	}

	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, activity.getString(R.string.lutfen_bekleyin), activity.getString(R.string.islem_yurutuluyor), true, true);
	}
	
	@Override
	protected List<Profil> doInBackground(String... params) {
		return arkadasEkle(params[0]);
	}
	
	private List<Profil> arkadasEkle(String arkadasKullaniciAdi) {
		
		String kullaniciAdi = getKullaniciAdi();
		if(TextUtils.isEmpty(kullaniciAdi)) {
			sonucKodu = PROFIL_BULUNAMADI_ERROR;
			return new ArrayList<Profil>();
		}
		
		publishProgress("Arkadaş ekleniyor...");
		
		sonucKodu = NetworkManager.arkadasEkle(kullaniciAdi, arkadasKullaniciAdi);
		
		if(BASARILI.equals(sonucKodu)) {
			publishProgress("Liste güncelleniyor...");
			return NetworkManager.arkadasSorgula(kullaniciAdi);
		}
			
		return new ArrayList<Profil>();
	}

	@Override
	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}
	
	@Override
	protected void onPostExecute(List<Profil> result) {
		
		String sonucMessage = getArkadasEkleSonucMessage(sonucKodu);
		Toast.makeText(activity, sonucMessage, Toast.LENGTH_LONG).show();
		
		if(result == null || result.size() == 0) {
			progressDialog.cancel();
			return;
		}

		recyclerView.setAdapter(new KimlerRecyclerViewAdapter(result, kimlerItemClickListener));
    	progressDialog.cancel();
		
	}

	private String getArkadasEkleSonucMessage(String sonuc) {
		
		if(BASARILI.equals(sonuc))
			return activity.getString(R.string.toast_arkadas_ekle_basarili);
		else if(ARKADAS_BULUNAMADI_ERROR.equals(sonuc))
			return activity.getString(R.string.toast_arkadas_profil_bulunamadi);
		else if(ARKADAS_ZATEN_MEVCUT_ERROR.equals(sonuc))
			return activity.getString(R.string.toast_arkadas_zaten_mevcut);
		else if(PROFIL_BULUNAMADI_ERROR.equals(sonuc))
			return activity.getString(R.string.toast_kayitli_profil_yok);
		else
			return activity.getString(R.string.toast_bilinmeyen_hata);
		
	}
	
	private String getKullaniciAdi() {
		
		DatabaseManager manager = new DatabaseManager(activity);
		
		Profil profil = manager.profilSorgula(null);
		
		if(profil == null)
			return null;
		
		return profil.getKullaniciAdi();
	}

}
