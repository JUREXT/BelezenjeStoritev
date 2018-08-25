package com.jurext.belezenje_storitve.belezenje_storitev;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Shrani_storitev extends Activity {


    Button izhod, shrani;
    EditText edit_stranka, edit_storitev, edit_kraj;

    TextView text_datum_zacetek, text_datum_konec, text_cas_zacetek, text_cas_konec, text_trajanje_ure_storitev;

    long cas_merjenja_dela=0;
    String datum_start, ura_start, datum_konec, ura_konec;

    Spinner spinner_kraj, spinner_storitev, spinner_oznaka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shrani_storitev);
        //intent.putExtra("cas_merjenja_dela", cas_merjenja_dela);
        //intent.putExtra("datum_start", datum_start);
        //intent.putExtra("ura_start", ura_start);

        cas_merjenja_dela = getIntent().getLongExtra("cas_merjenja_dela", 1825);
        datum_start = getIntent().getStringExtra("datum_start");
        ura_start = getIntent().getStringExtra("ura_start");
       // Log.d("jeba", "casDela "+cas_merjenja_dela+" datum_start "+datum_start+" ura_start "+ura_start);
        INIT();
        NALOZI_SPINNERJE();


        ////spinner kraj
        spinner_kraj.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               edit_kraj.setText(String.valueOf(spinner_kraj.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                edit_kraj.setText("");
            }
        });

        /////spinner storitev
        spinner_storitev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_storitev.setText(String.valueOf(spinner_storitev.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ///koda
            }
        });




    }/// konec onCreate

    public void INIT(){
        izhod=(Button)findViewById(R.id.btn_izhod_);
        shrani=(Button)findViewById(R.id.btn_shrani_);

        edit_stranka=(EditText)findViewById(R.id.edit_stranka_);
        edit_storitev=(EditText)findViewById(R.id.edit_storitev_);
        edit_kraj=(EditText)findViewById(R.id.edit_kraj_);

        text_datum_zacetek=(TextView)findViewById(R.id.text_datum_zacetek_);
        text_datum_konec=(TextView)findViewById(R.id.text_datum_konec_);

        text_cas_zacetek=(TextView)findViewById(R.id.text_cas_zacetek_);
        text_cas_konec=(TextView)findViewById(R.id.text_cas_konec_);

        text_trajanje_ure_storitev=(TextView)findViewById(R.id.text_trajanje_storitve);

        ////////////////////////////
        text_datum_zacetek.setText(datum_start);
        text_datum_konec.setText(getDatum());

        text_cas_zacetek.setText(ura_start);
        text_cas_konec.setText(getUra());

        text_trajanje_ure_storitev.setText(Pretvori_LongV_String(cas_merjenja_dela));
        /////////////////////////////
        spinner_kraj = (Spinner)findViewById(R.id.spinner_kraj);
        spinner_storitev = (Spinner)findViewById(R.id.spinner_storitev);
        spinner_oznaka = (Spinner)findViewById(R.id.spinner_oznaka);
    }

    public void NALOZI_SPINNERJE(){
       // Toast.makeText(this, "Spinerji Nalozeni", Toast.LENGTH_SHORT).show();
        Baza baza = new Baza(this);
        ///
        ArrayList<String> arrayList_storitev = baza.VRNE_SEZNAM_OPIS_STORITVE_TIP_STRING();
        if(arrayList_storitev.isEmpty()){
            arrayList_storitev.add("PRAZEN SEZNAM");
        }
        ArrayAdapter<String> arrayAdapter_storitev = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_design_vrstica, arrayList_storitev);
        spinner_storitev.setAdapter(arrayAdapter_storitev);
        ///
        ArrayList<String> arrayList_oznaka = baza.VRNE_SEZNAM_OZNAK_TIP_STRING();
        if(arrayList_oznaka.isEmpty()){
            arrayList_oznaka.add("PRAZEN SEZNAM");
        }
        ArrayAdapter<String> arrayAdapter_oznaka = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_design_vrstica, arrayList_oznaka);
        spinner_oznaka.setAdapter(arrayAdapter_oznaka);
        ///
        ArrayList<String> arrayList_lokacija = baza.VRNE_SEZNAM_LOKACIJ_TIP_STRING();
        if(arrayList_lokacija.isEmpty()){
            arrayList_lokacija.add("PRAZEN SEZNAM");
        }
        ArrayAdapter<String> arrayAdapter_lokacija = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_design_vrstica, arrayList_lokacija);
        spinner_kraj.setAdapter(arrayAdapter_lokacija);
        ///
        baza.close();
    }

    private String Pretvori_LongV_String(long cas){
        int ure = (int) (cas / 3600000);
        int minute = (int) (cas - ure * 3600000) / 60000;
        int sekunde = (int) (cas - ure * 3600000 - minute * 60000) / 1000;
        return ""+ure+":"+minute+":"+sekunde;
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

    public void IZHOD(View view){
        finish();
    }

    public void SHRANI(View view){
        //edit_stranka, edit_storitev, edit_kraj;
        if(edit_stranka.length() == 0){
            Toast.makeText(this,"Vnesi Stranko!!", Toast.LENGTH_LONG).show();
        }
        else if(edit_storitev.length() == 0){
            Toast.makeText(this,"Vnesi Storitev ali izberi iz seznama!!", Toast.LENGTH_LONG).show();
        }
        else if (edit_kraj.length() == 0){
            Toast.makeText(this,"Vnesi Kraj ali izberi iz seznama!!", Toast.LENGTH_LONG).show();
        }else {
            try {
                Baza baza = new Baza(this);
                Storitev storitev = new Storitev();  //id, storitev, stranka, kraj, cas_trajanja, datum_zacetek, datum_konec, cas_zacetek, cas_konec, TAGE_zahtevnost_dela;
                storitev.setStoritev(edit_storitev.getText().toString());
                String stranka_velike_crke = edit_stranka.getText().toString();
                stranka_velike_crke = stranka_velike_crke.toUpperCase();
                storitev.setStranka(stranka_velike_crke);
                storitev.setKraj(edit_kraj.getText().toString());
                storitev.setCas_trajanja(text_trajanje_ure_storitev.getText().toString());
                storitev.setDatum_zacetek(text_datum_zacetek.getText().toString());
                storitev.setDatum_konec(text_datum_konec.getText().toString());
                storitev.setCas_zacetek(text_cas_zacetek.getText().toString());
                storitev.setCas_konec(text_cas_konec.getText().toString());
                storitev.setTAGE_zahtevnost_dela(String.valueOf(spinner_oznaka.getSelectedItem()));
                baza.INSERT_STORITEV(storitev);
                baza.close();
                Toast.makeText(this, "Storitev shranjena", Toast.LENGTH_LONG).show();
                finish();
            }
            catch (Exception e){
                Toast.makeText(this, "Ni shranjeno ->"+ e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        //finish();
    }



}