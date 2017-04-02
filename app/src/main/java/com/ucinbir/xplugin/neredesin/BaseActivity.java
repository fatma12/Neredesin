package com.ucinbir.xplugin.neredesin;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by xplugin on 31.03.2017.
 */
public class BaseActivity extends ActionBarActivity {
    public static final String PROFILE_PHOTO_FILE_NAME="ProfilFotografim.jpg";
    public static final String NEREDESIN_BASE_URL= "http://10.0.2.2/neredesin";
    public static final String NEREDESIN_PROFIL_KAYDET_URL=NEREDESIN_BASE_URL+"profil_kaydet.php";
    public static final String NEREDESIN_ARKADAS_LISTELE_URL=NEREDESIN_BASE_URL+"arkadas_listele.php";
    public static final String NEREDESIN_ARKADAS_EKLE_URL=NEREDESIN_BASE_URL+"arkadas_ekle.php";
    public static final String NEREDESIN_KONUM_SORGULA_URL=NEREDESIN_BASE_URL+"konum_sorgula.php";
    public static final String NEREDESIN_KONUM_KAYDET_URL=NEREDESIN_BASE_URL+"konum_kaydet.php";
    public static final String ARKADAS_INTENT_EXTRA="arkadas";
    public static final String YAKIN_ARKADAS_BROADCAST="YAKIN_ARKADAS_BROADCAST";
}
