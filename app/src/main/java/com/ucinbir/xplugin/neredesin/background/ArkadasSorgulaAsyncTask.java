package com.ucinbir.xplugin.neredesin.background;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.adapter.KimlerRecyclerViewAdapter;
import com.ucinbir.xplugin.neredesin.database.DatabaseManager;
import com.ucinbir.xplugin.neredesin.database.Profil;

import java.util.ArrayList;
import java.util.List;

public class ArkadasSorgulaAsyncTask extends AsyncTask<Void, String, List<Profil>> {
	
	public static final String PROFIL_BULUNAMADI_ERROR = "-1";
	private String sonucKodu;

	private FragmentActivity activity;
	private KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener;
	private ProgressDialog progressDialog;
	private RecyclerView recyclerView;

	public ArkadasSorgulaAsyncTask(FragmentActivity activity, KimlerRecyclerViewAdapter.KimlerItemClickListener kimlerItemClickListener) {
		super();
		this.activity = activity;
		this.kimlerItemClickListener = kimlerItemClickListener;
		this.recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView);
	}

	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(activity, activity.getString(R.string.lutfen_bekleyin), activity.getString(R.string.islem_yurutuluyor), true, true);
	}

	@Override
	protected List<Profil> doInBackground(Void... params) {
		return getArkadasList();
	}

	private List<Profil> getArkadasList() {
		
		String kullaniciAdi = getKullaniciAdi();
		if(TextUtils.isEmpty(kullaniciAdi)) {
			sonucKodu = PROFIL_BULUNAMADI_ERROR;
			return new ArrayList<Profil>();
		}
		
		publishProgress("Arkadaş listesi sorgulanıyor...");
		
		return NetworkManager.arkadasSorgula(kullaniciAdi);
		
	}

	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}

	@Override
	protected void onPostExecute(List<Profil> result) {
		
		String sonucMessage = getArkadasSorgulaSonucMessage(sonucKodu);
		
		if(!TextUtils.isEmpty(sonucMessage)) {
			Toast.makeText(activity, sonucMessage, Toast.LENGTH_LONG).show();
			progressDialog.cancel();
			return;
		}
		
		if(result == null || result.size() == 0) {
    		String mesaj = activity.getString(R.string.toast_arkadas_bulunamadi);
    		Toast.makeText(activity, mesaj, Toast.LENGTH_LONG).show();
    		progressDialog.cancel();
			return;
    	}

		recyclerView.setAdapter(new KimlerRecyclerViewAdapter(result, kimlerItemClickListener));
    	progressDialog.cancel();

	}
	
	private String getArkadasSorgulaSonucMessage(String sonuc) {
		
		if(PROFIL_BULUNAMADI_ERROR.equals(sonuc))
			return activity.getResources().getString(R.string.toast_kayitli_profil_yok);
		
		return null;
		
	}
	
	private String getKullaniciAdi() {
		
		DatabaseManager manager = new DatabaseManager(activity);
		
		Profil profil = manager.profilSorgula(null);
		
		if(profil == null)
			return null;
		
		return profil.getKullaniciAdi();
	}

}
