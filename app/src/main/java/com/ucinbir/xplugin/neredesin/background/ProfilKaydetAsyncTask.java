package com.ucinbir.xplugin.neredesin.background;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.database.Profil;

public class ProfilKaydetAsyncTask extends AsyncTask<Profil, String, String>{
	
	public static final String BASARILI = "1";
	private Context context;
	private ProgressDialog progressDialog;

	public ProfilKaydetAsyncTask(Context context) {
		super();
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		progressDialog = ProgressDialog.show(context, context.getString(R.string.lutfen_bekleyin), context.getString(R.string.islem_yurutuluyor), true, true);
	}

	@Override
	protected String doInBackground(Profil... params) {
		return profilKaydet(params[0]);
	}
	
	private String profilKaydet(Profil profil) {
		publishProgress(context.getString(R.string.profiliniz_kaydediliyor));
		return NetworkManager.profilKaydet(profil);
	}
	
	@Override
	protected void onProgressUpdate(String... progress) {
		progressDialog.setMessage(progress[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		String profilKaydetSonucMessage = getProfilKaydetSonucMessage(result);
		Toast.makeText(context, profilKaydetSonucMessage, Toast.LENGTH_LONG).show();
		progressDialog.cancel();
	}
	
	private String getProfilKaydetSonucMessage(String sonuc) {
		
		if(BASARILI.equals(sonuc))
			return context.getResources().getString(R.string.toast_profil_kaydet_basarili);
		
		return context.getResources().getString(R.string.toast_bilinmeyen_hata);
	}

}
