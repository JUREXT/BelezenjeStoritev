package com.jurext.belezenje_storitve.belezenje_storitev;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

//// OK

public class MainActivity extends Activity {
    Button start_storitev, koncaj_storitev, pregled_storitev, reset;
    Chronometer chr_delo;

    boolean Is_delo=false;
    long timeWhenStopped = 0;
    boolean isPrvi_zagon=false;
    long cas_merjenja_dela=0;
    String datum_start, ura_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        INIT();
        koncaj_storitev.setEnabled(false);
        koncaj_storitev.setBackgroundColor(getResources().getColor(R.color.siva));
        reset.setEnabled(false);
        reset.setBackgroundColor(getResources().getColor(R.color.siva));
    }

    public void START(View view){
        if (isPrvi_zagon==false){
            datum_start = getDatum();
            ura_start = getUra();
            isPrvi_zagon=true;
            koncaj_storitev.setEnabled(true);
            reset.setEnabled(true);
            koncaj_storitev.setBackgroundColor(getResources().getColor(R.color.srednje_modra));
            koncaj_storitev.setTextColor(getResources().getColor(R.color.svetlo_modra));
            reset.setBackgroundColor(getResources().getColor(R.color.srednje_modra));
            reset.setTextColor(getResources().getColor(R.color.svetlo_modra));
        }
        if (Is_delo==false){
            Is_delo=true;
            start_storitev.setText("Začni s Premorom ||");
            //start delo
            chr_delo.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
            chr_delo.start();
        }
        else {
            Is_delo=false;
            start_storitev.setText("Nadaljuj Storitev...");
            timeWhenStopped = chr_delo.getBase() - SystemClock.elapsedRealtime();
            chr_delo.stop();
            ////////////////
        }
    }

    public void KONCAJSTORITEV(View view){
        cas_merjenja_dela = SystemClock.elapsedRealtime() - chr_delo.getBase();
        chr_delo.stop();
        Intent intent = new Intent(this, Shrani_storitev.class);
        intent.putExtra("cas_merjenja_dela", cas_merjenja_dela);
        intent.putExtra("datum_start", datum_start);
        intent.putExtra("ura_start", ura_start);
        Reset();
        koncaj_storitev.setEnabled(false);
        startActivity(intent);
    }

    public void PREGLED_STORITEV(View view){
        Intent intent = new Intent(this, Prikaz_storitev.class);
        startActivity(intent);
    }

    public void Reset(){
        // Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
        Is_delo=false;
        start_storitev.setText("Začni Storitev");
        //
        chr_delo.stop();
        chr_delo.setBase(SystemClock.elapsedRealtime());
        //
        isPrvi_zagon=false;
        //
        datum_start="";
        ura_start="";
        timeWhenStopped = 0;
        cas_merjenja_dela=0;
        ////////////
        koncaj_storitev.setEnabled(false);
        koncaj_storitev.setBackgroundColor(getResources().getColor(R.color.siva));
        reset.setEnabled(false);
        reset.setBackgroundColor(getResources().getColor(R.color.siva));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void RESET(View view){
        Reset();
    }
    private void INIT(){
        start_storitev=(Button)findViewById(R.id.btn_start);
        koncaj_storitev=(Button)findViewById(R.id.btn_stop);
        pregled_storitev=(Button)findViewById(R.id.btn_pregled);
        reset=(Button)findViewById(R.id.btn_reset);
        chr_delo=(Chronometer)findViewById(R.id.belezi_delo_);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
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

    public void odpriNastavitve(View view){
        Intent intent = new Intent(this, Nastavitve.class);
        startActivity(intent);
    }


}