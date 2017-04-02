package com.ucinbir.xplugin.neredesin.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ucinbir.xplugin.neredesin.R;
import com.ucinbir.xplugin.neredesin.background.ProfilKaydetAsyncTask;
import com.ucinbir.xplugin.neredesin.database.DatabaseManager;
import com.ucinbir.xplugin.neredesin.database.Profil;

public class ProfilFragment extends BaseFragment {

    private DatabaseManager manager;
    private static final int CAMERA_REQUEST = 1;

    private ImageButton profilImageButton;
    private Bitmap profilPhoto;
    private EditText kullaniciAdiEditText;
    private EditText adEditText;
    private EditText soyadEditText;
    private EditText telefonEditText;
    private EditText emailEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ekranKontrolleriniOlustur();
    }

    private void ekranKontrolleriniOlustur() {

        final FragmentActivity activity = getActivity();

        manager = new DatabaseManager(activity);

        final Profil profil = manager.profilSorgula(null);

        profilImageButton = (ImageButton) activity.findViewById(R.id.profilImageButton);
        kullaniciAdiEditText = (EditText) activity.findViewById(R.id.kullanıcıAdıEditText);
        adEditText = (EditText) activity.findViewById(R.id.adEditText);
        soyadEditText = (EditText) activity.findViewById(R.id.soyadEditText);
        telefonEditText = (EditText) activity.findViewById(R.id.telefonEditText);
        emailEditText = (EditText) activity.findViewById(R.id.emailEditText);

        ekranGuncelle(profil);

        Button kaydetButton = (Button) activity.findViewById(R.id.kaydetButton);
        kaydetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Profil kaydedilecekProfil = ekranDegerleriniOku();

                int sonuc = manager.profilKaydetGuncelle(kaydedilecekProfil);

                if (DatabaseManager.BASARILI == sonuc) {
                    ProfilKaydetAsyncTask task = new ProfilKaydetAsyncTask(activity);
                    task.execute(kaydedilecekProfil);
                } else {
                    String profilKaydetSonucMessage = getProfilKaydetSonucMessage(sonuc);
                    Toast.makeText(activity, profilKaydetSonucMessage, Toast.LENGTH_LONG).show();
                }

            }
        });

        profilImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

    }

    private void ekranGuncelle(Profil profil) {

        if(profil == null)
            return;

        kullaniciAdiEditText.setText(profil.getKullaniciAdi());
        kullaniciAdiEditText.setEnabled(false);

        adEditText.setText(profil.getAd());
        soyadEditText.setText(profil.getSoyad());
        telefonEditText.setText(profil.getTelefon());
        emailEditText.setText(profil.getEmail());

        if(profil.getProfilPhoto() != null)
            profilImageButton.setImageBitmap(profil.getProfilPhoto());

    }

    private Profil ekranDegerleriniOku() {

        Profil profil = new Profil();

        if(kullaniciAdiEditText.getText() != null)
            profil.setKullaniciAdi(kullaniciAdiEditText.getText().toString());

        if(adEditText.getText() != null)
            profil.setAd(adEditText.getText().toString());

        if(soyadEditText.getText() != null)
            profil.setSoyad(soyadEditText.getText().toString());

        if(telefonEditText.getText() != null)
            profil.setTelefon(telefonEditText.getText().toString());

        if(emailEditText.getText() != null)
            profil.setEmail(emailEditText.getText().toString());

        if(profilPhoto != null)
            profil.setProfilPhoto(profilPhoto);

        return profil;
    }

    private String getProfilKaydetSonucMessage(int sonuc) {

        if(DatabaseManager.BASARILI == sonuc)
            return getResources().getString(R.string.toast_profil_kaydet_basarili);

        if(DatabaseManager.PROFIL_VALIDASYON_HATASI == sonuc)
            return getResources().getString(R.string.toast_profil_validasyon_hatasi);

        return getResources().getString(R.string.toast_bilinmeyen_hata);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case CAMERA_REQUEST:

                if(resultCode == Activity.RESULT_OK) {
                    profilPhoto = (Bitmap) data.getExtras().get("data");

                    if(profilPhoto != null) {
                        profilPhoto = profilPhotoYenidenBoyutlandir(profilPhoto);
                        profilImageButton.setImageBitmap(null);
                        profilImageButton.setImageBitmap(profilPhoto);
                    }
                }

                break;
        }

    }

    private Bitmap profilPhotoYenidenBoyutlandir(Bitmap profilPhoto) {

        int size = 200;
        int en = profilPhoto.getWidth();
        int boy = profilPhoto.getHeight();

        int yeniEn = (en >= boy) ? size : (int) ((float) size * ((float) en / (float) boy));
        int yeniBoy = (boy >= en) ? size : (int) ((float) size * ((float) boy / (float) en));

        return Bitmap.createScaledBitmap(profilPhoto, yeniEn, yeniBoy, true);

    }

}
