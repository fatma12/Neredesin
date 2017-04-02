package com.ucinbir.xplugin.neredesin.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by xplugin on 01.04.2017.
 */
public class DatabaseManager {
    public static final int BASARILI = 1;
    public static final int BILINMEYEN_HATA = -1;
    public static final int PROFIL_VALIDASYON_HATASI = -2;
    private DatabaseHelper helper;
    private Context context;

    public DatabaseManager(Context context) {
        this.context = context;
        helper = new DatabaseHelper(context);
    }

    public Profil profilSorgula(String kullaniciAdi) {
        String where = null;
        String[] whereArgs = null;
        if (!TextUtils.isEmpty(kullaniciAdi)) {
            where = NeredesinDatabaseContract.Profil.COLUMN_KULLANICI_ADI + "=?";
            whereArgs = new String[]{kullaniciAdi};
        }
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(NeredesinDatabaseContract.TABLE_NAME, NeredesinDatabaseContract.Profil.FULL_PROJECTION, where, whereArgs, null, null, null);
        return buildProfil(cursor);
    }

    private Profil buildProfil(Cursor cursor){
        if(cursor==null || cursor.getCount() !=1 || !cursor.moveToNext())
        return null;
        Profil profil = new Profil();
        int idIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil._ID);
        profil.setId(cursor.getInt(idIndex));
        int kullaniciAdiIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil.COLUMN_KULLANICI_ADI);
        profil.setKullaniciAdi(cursor.getString(kullaniciAdiIndex));
        int adIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil.COLUMN_AD);
        profil.setAd(cursor.getString(adIndex));
        int soyadIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil.Column_SOYAD);
        profil.setSoyad(cursor.getString(soyadIndex));
        int telefonIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil.COLUMN_TELEFON);
        profil.setTelefon(cursor.getString(telefonIndex));
        int emailIndex=cursor.getColumnIndex(NeredesinDatabaseContract.Profil.COLUMN_EMAIL);
        profil.setEmail(cursor.getString(emailIndex));
        Bitmap profilPhoto=profilPhotoSorgula();
        profil.setProfilPhoto(profilPhoto);
        return profil;
    }
    public int profilKaydetGuncelle(Profil profil){
        ContentValues satir=new ContentValues();
        satir.put(NeredesinDatabaseContract.Profil.COLUMN_KULLANICI_ADI,profil.getKullaniciAdi());
        satir.put(NeredesinDatabaseContract.Profil.COLUMN_AD, profil.getAd());
        satir.put(NeredesinDatabaseContract.Profil.Column_SOYAD, profil.getSoyad());
        satir.put(NeredesinDatabaseContract.Profil.COLUMN_TELEFON, profil.getTelefon());
        satir.put(NeredesinDatabaseContract.Profil.COLUMN_EMAIL, profil.getEmail());
        profilPhotoKaydet(profil.getProfilPhoto());
        Profil kayitliProfil=profilSorgula(profil.getKullaniciAdi());
        if(kayitliProfil !=null)
            return profilGuncelle(kayitliProfil.getId(),satir);
        return profilKaydet(satir);
    }
    public int profilKaydet(ContentValues satir){
        SQLiteDatabase db=helper.getWritableDatabase();
        long profilId=db.insert(NeredesinDatabaseContract.TABLE_NAME, null, satir);
        if(profilId==-1)
            return BILINMEYEN_HATA;
        return BASARILI;
    }
    public int profilGuncelle(int id,ContentValues satir){
        SQLiteDatabase db=helper.getWritableDatabase();
        String where=NeredesinDatabaseContract.Profil._ID+"="+id;
        int guncellenenSatirSayisi=db.update(NeredesinDatabaseContract.TABLE_NAME, satir, where, null);
        if (guncellenenSatirSayisi!=1)
            return BILINMEYEN_HATA;
        return BASARILI;
    }
    private void profilPhotoKaydet(Bitmap profilPhoto){
        try{
            FileOutputStream fos=context.openFileOutput("ProfilFotografim.jpg",Context.MODE_PRIVATE);
            profilPhoto.compress(Bitmap.CompressFormat.JPEG,100,fos);
            fos.close();
        }
        catch (Exception e){
            Log.e("DatabaseManager","Profil Fotografı kaydedilirken hata olustu",e);

        }
    }
    private Bitmap profilPhotoSorgula(){
        File icBellekAdres=context.getFilesDir();
        File profilPhotoFile=new File(icBellekAdres,"ProfilFotografim.jpg");
        if (!profilPhotoFile.exists())
            return null;
        return BitmapFactory.decodeFile(profilPhotoFile.getAbsolutePath());
    }
}
