package com.jurext.belezenje_storitve.belezenje_storitev;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;



public class Baza extends SQLiteOpenHelper {
    public static final int verzija = 1;
    public static final String ime_baze = "storitve.db";
    //////////////////////////////////////////////////////////////
    public static final String tabela_storitev = "storitve";
    //////
    public static final String id_storitev = "id";
    public static final String storitev = "storitev";
    public static final String stranka = "stranka";
    public static final String kraj = "kraj";
    public static final String cas_trajanja = "cas_trajanja";
    public static final String datum_zacetek = "datum_zacetek";
    public static final String datum_konec = "datum_konec";
    public static final String cas_zacetek = "cas_zacetek";
    public static final String cas_konec = "cas_konec";
    public static final String TAGE_zahtevnost_dela = "TAGE_zahtevnost_dela";
    //////////////////////////////////////////////////////////////
    public static final String tabela_lokacija = "lokacija";
    //////
    public static final String id_lokacija = "id_lokacija";
    public static final String lokacija = "lokacija";
    //////////////////////////////////////////////////////////////
    public static final String tabela_oznaka = "oznaka";
    //////
    public static final String id_oznaka = "id_oznaka";
    public static final String oznaka_tag = "oznaka_tag";
    //////////////////////////////////////////////////////////////
    public static final String tabela_opis_storitev = "tabela_opis_storitev";
    //////
    public static final String id_opis_storitev = "id_opis_storitev";
    public static final String opis_storitev = "opis_storitev";


    public Baza(Context context) {
        super(context, ime_baze, null, verzija);
    }

    @Override
    public void onCreate(SQLiteDatabase baza) {
        String ustvari_tabelo_storitev = "CREATE TABLE " + tabela_storitev + "(" + id_storitev + " INTEGER PRIMARY KEY, " + storitev + " TEXT, " + stranka + " TEXT, " + kraj + " TEXT, " + cas_trajanja + " TEXT, " + datum_zacetek + " TEXT, "
                + datum_konec + " TEXT, " + cas_zacetek + " TEXT, " + cas_konec + " TEXT ," + TAGE_zahtevnost_dela + " TEXT " + ");";
        baza.execSQL(ustvari_tabelo_storitev);
        ////
        String ustvari_tabelo_lokacija = "CREATE TABLE " + tabela_lokacija + "(" + id_lokacija + " INTEGER PRIMARY KEY, " + lokacija + " TEXT " + ");";
        baza.execSQL(ustvari_tabelo_lokacija);
        ////
        String ustvari_tabelo_oznaka = "CREATE TABLE " + tabela_oznaka + "(" + id_oznaka + " INTEGER PRIMARY KEY, " + oznaka_tag + " TEXT " + ");";
        baza.execSQL(ustvari_tabelo_oznaka);
        ////
        String ustvari_tabelo_opis_storitev = "CREATE TABLE " + tabela_opis_storitev + "(" + id_opis_storitev + " INTEGER PRIMARY KEY, " + opis_storitev + " TEXT " + ");";
        baza.execSQL(ustvari_tabelo_opis_storitev);

    }

    @Override
    public void onUpgrade(SQLiteDatabase baza, int i, int i1) {
        String drop_table_storitev = "DROP TBALE IF EXIST " + tabela_storitev;
        baza.execSQL(drop_table_storitev);
        ////
        String drop_table_lokacija = "DROP TABLE IF EXIST " + tabela_lokacija;
        baza.execSQL(drop_table_lokacija);
        ////
        String drop_table_oznaka = "DROP TABLE IF EXIST " + tabela_oznaka;
        baza.execSQL(drop_table_oznaka);
        ////
        String drop_table_opis_storitve = "DROP TABLE IF EXIST " + tabela_opis_storitev;
        baza.execSQL(drop_table_opis_storitve);
        ////
        onCreate(baza);
    }

    public int VELIKOST_BAZE_STORITEV() {
        SQLiteDatabase baza = this.getReadableDatabase();
        String ukaz = "SELECT * FROM " + tabela_storitev;
        Cursor cursor = baza.rawQuery(ukaz, null);
        int velikost = cursor.getCount();
        cursor.close();
        baza.close();
        return velikost;
    }

    public void INSERT_STORITEV(Storitev storitev) {
        SQLiteDatabase baza = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.storitev, storitev.getStoritev());
        values.put(stranka, storitev.getStranka());
        values.put(kraj, storitev.getKraj());
        values.put(cas_trajanja, storitev.getCas_trajanja());
        values.put(datum_zacetek, storitev.getDatum_zacetek());
        values.put(datum_konec, storitev.getDatum_zacetek());
        values.put(cas_zacetek, storitev.getCas_zacetek());
        values.put(cas_konec, storitev.getCas_konec());
        values.put(TAGE_zahtevnost_dela, storitev.getTAGE_zahtevnost_dela());
        baza.insert(tabela_storitev, null, values);
        baza.close();
    }

    public ArrayList<Storitev> VRNE_SEZNAM_STORITEV() {
        ArrayList<Storitev> seznam = new ArrayList<>();
        String ukaz = "SELECT * FROM " + tabela_storitev;
        SQLiteDatabase baza = this.getReadableDatabase();
        Cursor cursor = baza.rawQuery(ukaz, null);
        if (cursor.moveToFirst()) {
            do {
                Storitev storitev = new Storitev();     /// id, storitev, stranka, kraj, cas_trajanja, datum_zacetek, datum_konec, cas_zacetek, cas_konec, TAGE_zahtevnost_dela
                storitev.setId(Integer.parseInt(cursor.getString(0)));
                storitev.setStoritev(cursor.getString(1));
                storitev.setStranka(cursor.getString(2));
                storitev.setKraj(cursor.getString(3));
                storitev.setCas_trajanja(cursor.getString(4));
                storitev.setDatum_zacetek(cursor.getString(5));
                storitev.setDatum_konec(cursor.getString(6));
                storitev.setCas_zacetek(cursor.getString(7));
                storitev.setCas_konec(cursor.getString(8));
                storitev.setTAGE_zahtevnost_dela(cursor.getString(9));
                seznam.add(storitev);
            } while (cursor.moveToNext());
        }
        baza.close();
        return seznam;
    }

    public void BRISANJA_VSEH_VNOSOV_STORITEV() {
        SQLiteDatabase baza = this.getWritableDatabase();
        String ukaz = "DELETE FROM " + tabela_storitev;
        baza.execSQL(ukaz);
        baza.close();
    }


    public Cursor VRNI_VSE_BAZA_C_STORITEV() {
        SQLiteDatabase baza = this.getReadableDatabase();
        String ukaz = "SELECT * FROM " + tabela_storitev;
        Cursor data = baza.rawQuery(ukaz, null);
        return data;
    }
    //////////////////////////////////////////////////////// funkcije -> baza -> lokacija

    public void INSERT_LOKACIJA(String lokacija) {
        SQLiteDatabase baza = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.lokacija, lokacija);
        baza.insert(tabela_lokacija, null, values);
        baza.close();
    }


    public ArrayList<String> VRNE_SEZNAM_LOKACIJ_TIP_STRING() {
        ArrayList<String> seznam = new ArrayList<>();
        String ukaz = "SELECT * FROM " + tabela_lokacija;
        SQLiteDatabase baza = this.getReadableDatabase();
        Cursor cursor = baza.rawQuery(ukaz, null);
        String lokacija;
        if (cursor.moveToFirst()) {
            do {
                lokacija = cursor.getString(1);
                seznam.add(lokacija);
            } while (cursor.moveToNext());
        }
        baza.close();
        return seznam;
    }

    public void BRISANJA_VSEH_VNOSOV_LOKACIJ() {
        SQLiteDatabase baza = this.getWritableDatabase();
        String ukaz = "DELETE FROM " + tabela_lokacija;
        baza.execSQL(ukaz);
        baza.close();
    }

    public int VELIKOST_BAZE_LOKACIJ() {
        SQLiteDatabase baza = this.getReadableDatabase();
        String ukaz = "SELECT * FROM " + tabela_lokacija;
        Cursor cursor = baza.rawQuery(ukaz, null);
        int velikost = cursor.getCount();
        cursor.close();
        baza.close();
        return velikost;
    }

    //////////////////////////////////////////////////////// funkcije -> baza -> oznaka

    public void INSERT_OZNAKA(String oznaka) {
        SQLiteDatabase baza = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(this.oznaka_tag, oznaka);
        baza.insert(tabela_oznaka, null, values);
        baza.close();
    }


    public ArrayList<String> VRNE_SEZNAM_OZNAK_TIP_STRING() {
        ArrayList<String> seznam = new ArrayList<>();
        String ukaz = "SELECT * FROM " + tabela_oznaka;
        SQLiteDatabase baza = this.getReadableDatabase();
        Cursor cursor = baza.rawQuery(ukaz, null);
        String oznaka;
        if (cursor.moveToFirst()) {
            do {
                oznaka = cursor.getString(1);
                seznam.add(oznaka);
            } while (cursor.moveToNext());
        }
        baza.close();
        return seznam;
    }

    public void BRISANJA_VSEH_VNOSOV_OZNAK() {
        SQLiteDatabase baza = this.getWritableDatabase();
        String ukaz = "DELETE FROM " + tabela_oznaka;
        baza.execSQL(ukaz);
        baza.close();
    }

    public int VELIKOST_BAZE_OZNAK() {
        SQLiteDatabase baza = this.getReadableDatabase();
        String ukaz = "SELECT * FROM " + tabela_oznaka;
        Cursor cursor = baza.rawQuery(ukaz, null);
        int velikost = cursor.getCount();
        cursor.close();
        baza.close();
        return velikost;
    }


    //////////////////////////////////////////////////////// funkcije -> baza -> storitev


    public void INSERT_OPIS_STORITVE(String opis) {
        SQLiteDatabase baza = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(opis_storitev, opis);
        baza.insert(tabela_opis_storitev, null, values);
        baza.close();
    }


    public ArrayList<String> VRNE_SEZNAM_OPIS_STORITVE_TIP_STRING() {
        ArrayList<String> seznam = new ArrayList<>();
        String ukaz = "SELECT * FROM " + tabela_opis_storitev;
        SQLiteDatabase baza = this.getReadableDatabase();
        Cursor cursor = baza.rawQuery(ukaz, null);
        String oznaka;
        if (cursor.moveToFirst()) {
            do {
                oznaka = cursor.getString(1);
                seznam.add(oznaka);
            } while (cursor.moveToNext());
        }
        baza.close();
        return seznam;
    }

    public void BRISANJA_VSEH_VNOSOV_OPIS_STORITVE() {
        SQLiteDatabase baza = this.getWritableDatabase();
        String ukaz = "DELETE FROM " + tabela_opis_storitev;
        baza.execSQL(ukaz);
        baza.close();
    }

    public int VELIKOST_BAZE_OPIS_STORITEV() {
        SQLiteDatabase baza = this.getReadableDatabase();
        String ukaz = "SELECT * FROM " + tabela_opis_storitev;
        Cursor cursor = baza.rawQuery(ukaz, null);
        int velikost = cursor.getCount();
        cursor.close();
        baza.close();
        return velikost;
    }



}///konec baza