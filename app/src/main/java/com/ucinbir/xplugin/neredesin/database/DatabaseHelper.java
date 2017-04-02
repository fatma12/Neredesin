package com.ucinbir.xplugin.neredesin.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xplugin on 01.04.2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_CREATE="CREATE TABLE"+NeredesinDatabaseContract.TABLE_NAME+"("+NeredesinDatabaseContract.Profil._ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+NeredesinDatabaseContract.Profil.COLUMN_KULLANICI_ADI+"VARCHAR(20) NOT NULL,"+NeredesinDatabaseContract.Profil.COLUMN_AD+"VARCHAR(20) NOT NULL,"+NeredesinDatabaseContract.Profil.Column_SOYAD+"VARCHAR(20) NOT NULL,"+NeredesinDatabaseContract.Profil.COLUMN_TELEFON+"VARCHAR(10),"+NeredesinDatabaseContract.Profil.COLUMN_EMAIL+"VARCHAR(20));";
    public static final String DATABASE_DROP="DROP TABLE IF EXISTS"+NeredesinDatabaseContract.TABLE_NAME;
    public DatabaseHelper(Context context){super(context,NeredesinDatabaseContract.DATABASE_NAME,null,NeredesinDatabaseContract.DATABASE_VERSION);}
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        Log.w("DatabaseHelper","Veritabani"+oldVersion+"\'dan"+newVersion+"\'a guncelleniyor");
        db.execSQL(DATABASE_DROP);
        onCreate(db);
    }
}
