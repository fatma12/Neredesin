package com.ucinbir.xplugin.neredesin.database;

import android.graphics.Bitmap;

/**
 * Created by xplugin on 01.04.2017.
 */
public class Profil {
    private int id;
    private String kullaniciAdi;
    private String ad;
    private String soyad;
    private String telefon;
    private String email;
    private Bitmap profilPhoto;
    public Profil(){
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getKullaniciAdi(){
        return kullaniciAdi;
    }
    public void setKullaniciAdi(String kullaniciAdi){
        this.kullaniciAdi=kullaniciAdi;
    }
    public String getAd(){
        return ad;
    }
    public void setAd(String ad){
        this.ad=ad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Bitmap getProfilPhoto() {
        return profilPhoto;
    }

    public void setProfilPhoto(Bitmap profilPhoto) {
        this.profilPhoto = profilPhoto;
    }
}
