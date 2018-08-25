package com.jurext.belezenje_storitve.belezenje_storitev;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class Prikaz_storitev extends Activity {
    private static final int KODA_PRAVICE=1;
    Button brisanje, export;
    Baza baza;
    ArrayList<Storitev> seznam;
    Adapter_storitev adapter_storitev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prikaz_storitev);
        brisanje=(Button)findViewById(R.id.btn_brisi);
        export=(Button)findViewById(R.id.btn_export);
        ////
        baza = new Baza(this);
        seznam = new ArrayList<Storitev>();
        seznam = baza.VRNE_SEZNAM_STORITEV();
        baza.close();
        adapter_storitev = new Adapter_storitev(this, seznam);
        ListView moj_listview =(ListView)findViewById(R.id.listview);
        moj_listview.setAdapter(adapter_storitev);
    }

    public void POBRISI_BAZO(View view){
       final Baza baza = new Baza(this);
        new AlertDialog.Builder(this).setTitle("Brisanje baze!!").setMessage("Si prepričan, da želiš pobrisati vse vnose?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (0 != baza.VELIKOST_BAZE_STORITEV()){
                    Brisanje();
                    Toast.makeText(getBaseContext(), "Vnosi v Bazi so pobrisani!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getBaseContext(), "V bazi ni vnosov za brisanje!", Toast.LENGTH_LONG).show();
                    baza.close();
                }

            }
        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
                // Toast.makeText(getBaseContext(), "NE", Toast.LENGTH_LONG).show();
            }}).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    private void Brisanje(){
        seznam.clear();
        adapter_storitev.clear();
        Baza baza = new Baza(this);
        baza.BRISANJA_VSEH_VNOSOV_STORITEV();
        baza.close();
    }

    public void EXPORT(View view){
        if (imaPravico()){
           // EXPORT_CSV();
            new AsycExport().execute(); /// dela na svojem thread
        }
        else {
            prosiZaPravico();
        }
    }

    private String getDatum() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            String date = sdf.format(new Date());
            return date;
        } catch (Exception e) {
            /// Log.d("datum", e.toString());
            Toast.makeText(this, "Napaka->getDatum", Toast.LENGTH_SHORT).show();

        }
        return "Napaka-getDatum";
    }

    private  String getUra() {
        try {
            long yourmilliseconds = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            Date resultdate = new Date(yourmilliseconds);
            return sdf.format(resultdate);
        }catch (Exception e){
            Toast.makeText(this, "Napaka->getUra", Toast.LENGTH_SHORT).show();
        }
        return  "Napaka->getUra";
    }

    private boolean EXPORT_CSV(){
        /**First of all we check if the external storage of the device is available for writing.
         * Remember that the external storage is not necessarily the sd card. Very often it is
         * the device storage.
         */
        /// zapiši samo, če je kaj v bazi
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return false;
        }
        else {
            //We use the Download directory for saving our .csv file.
            //File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File mapa = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Beleženje Storitev"); //naredi svojo mapo od aplikacije, kamor daje potem datoteke
            if (!mapa.exists())
            {
                mapa.mkdirs();
            }

            File datoteka;
            PrintWriter printWriter = null;
            Baza baza = new Baza(this);
            if (baza.VELIKOST_BAZE_STORITEV() !=0) //ce je baza prazna ne bo nič zapisovalo
            {
                try {
                    datoteka = new File(mapa, "Seznam_Storitev_" + getDatum().toString() + "_" + getUra().toString() + ".csv"); // ime datoteke
                    datoteka.createNewFile();
                    printWriter = new PrintWriter(new FileWriter(datoteka));

                    Cursor curCSV = baza.VRNI_VSE_BAZA_C_STORITEV();

                    printWriter.println("ID,STORITEV,STRANKA,KRAJ,CAS_TRAJANJA,DATUM_ZACETEK,DATUM_KONEC,CAS_ZACETEK,CAS_KONEC");
                    while (curCSV.moveToNext()) {
                        String ID = curCSV.getString(0);
                        String STORITEV = curCSV.getString(1);
                        String STRANKA = curCSV.getString(2);
                        String KRAJ = curCSV.getString(3);
                        String CAS_TRAJANJA = curCSV.getString(4);
                        String DATUM_ZACETEK = curCSV.getString(5);
                        String DATUM_KONEC = curCSV.getString(6);
                        String CAS_ZACETEK = curCSV.getString(7);
                        String CAS_KONEC = curCSV.getString(8);
                        /**Create the line to write in the .csv file.
                         * We need a String where values are comma separated.
                         *
                         */
                        String record = ID + "," + STORITEV + "," + STRANKA + "," + KRAJ + "," + CAS_TRAJANJA + "," + DATUM_ZACETEK + "," + DATUM_KONEC + "," + CAS_ZACETEK + "," + CAS_KONEC;
                        printWriter.println(record); //write the record in the .csv file
                    }
                    curCSV.close();
                    baza.close();
                    Toast.makeText(this, "Export Končan", Toast.LENGTH_LONG).show();
                }


            catch(Exception exc) {
                //if there are any exceptions, return false
               // Log.d("napaka1", exc.toString());
                return false;
            }
            finally {
                if(printWriter != null)
                    printWriter.close();
            }
            //If there are no errors, return true.
            return true;
        }
            else {
                Toast.makeText(this, "Baza je prazna, ni podatkov za Export", Toast.LENGTH_LONG).show();
                baza.close();
            }
    }
        return false;
    }

    private void prosiZaPravico(){
        String pravice[] = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(pravice, KODA_PRAVICE);
        }
    }

    private boolean imaPravico(){
        int res =0;
        String pravice[] = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String pravica : pravice){
            res = checkCallingOrSelfPermission(pravica);
            if (!(res == PackageManager.PERMISSION_GRANTED)){
                return  false;
            }
        }
        return  true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean jeDovoljeno= true;
        switch (requestCode){
            case KODA_PRAVICE:
                for (int res : grantResults){
                    jeDovoljeno = jeDovoljeno && (res == PackageManager.PERMISSION_GRANTED);
                }
                break;
            default:
                jeDovoljeno = false;
                break;
        }

        if(jeDovoljeno){
            EXPORT_CSV();
        }
        else {
            //opozorimo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                    Toast.makeText(this, "Pravica je zavrjena", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    class AsycExport extends AsyncTask<Void,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            Toast.makeText(getBaseContext(), "Export se je začel!!", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean){
                Toast.makeText(getBaseContext(), "Export je končan!!", Toast.LENGTH_LONG).show();
            }else if (!aBoolean)
            {
                Toast.makeText(getBaseContext(), "Export se ni izvedel!!", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            /// zapiši samo, če je kaj v bazi
            String state = Environment.getExternalStorageState();
            if (!Environment.MEDIA_MOUNTED.equals(state)) {
                return false;
            }
            else {
                //File exportDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File mapa = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Beleženje Storitev"); //naredi svojo mapo od aplikacije, kamor daje potem datoteke
                if (!mapa.exists())
                {
                    mapa.mkdirs();
                }
                File datoteka;
                PrintWriter printWriter = null;
                Baza baza = new Baza(getBaseContext());
                if (baza.VELIKOST_BAZE_STORITEV() !=0) //ce je baza prazna ne bo nič zapisovalo
                {
                    try {
                        datoteka = new File(mapa, "Seznam_Storitev_" + getDatum().toString() + "_" + getUra().toString() + ".csv");
                        datoteka.createNewFile();
                        printWriter = new PrintWriter(new FileWriter(datoteka));

                        Cursor curCSV = baza.VRNI_VSE_BAZA_C_STORITEV();

                        printWriter.println("ID,STORITEV,STRANKA,KRAJ,CAS_TRAJANJA,DATUM_ZACETEK,DATUM_KONEC,CAS_ZACETEK,CAS_KONEC,ZAHTEVNOST_DELA"); //prvi zapis so imena stoplcev
                        while (curCSV.moveToNext()) {
                            String ID = curCSV.getString(0);
                            String STORITEV = curCSV.getString(1);
                            String STRANKA = curCSV.getString(2);
                            String KRAJ = curCSV.getString(3);
                            String CAS_TRAJANJA = curCSV.getString(4);
                            String DATUM_ZACETEK = curCSV.getString(5);
                            String DATUM_KONEC = curCSV.getString(6);
                            String CAS_ZACETEK = curCSV.getString(7);
                            String CAS_KONEC = curCSV.getString(8);
                            String ZAHTEVNOST_DELA = curCSV.getString(9);
                            /**Create the line to Write in the .csv file.
                             * We need a String where values are comma separated.
                             *
                             */
                            String record = ID + "," + STORITEV + "," + STRANKA + "," + KRAJ + "," + CAS_TRAJANJA + "," + DATUM_ZACETEK + "," + DATUM_KONEC + "," + CAS_ZACETEK + "," + CAS_KONEC+","+ZAHTEVNOST_DELA;
                            printWriter.println(record); //write the record in the .csv file
                        }
                        curCSV.close();
                        baza.close();
                       // Toast.makeText(this, "Export Končan", Toast.LENGTH_LONG).show();
                    }
                    catch(Exception exc) {
                        //if there are any exceptions, return false
                        // Log.d("napaka1", exc.toString());
                        return false;
                    }
                    finally {
                        if(printWriter != null)
                            printWriter.close();
                    }
                    //If there are no errors, return true.
                    return true;
                }
                else {
                   // Toast.makeText(this, "Baza je prazna, ni podatkov za Export", Toast.LENGTH_LONG).show();
                    baza.close();
                }
            }
            return false;
        }
    }


}/////////////