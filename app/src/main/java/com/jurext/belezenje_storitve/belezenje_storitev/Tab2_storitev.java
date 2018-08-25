package com.jurext.belezenje_storitve.belezenje_storitev;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Jure_Lokovsek on 4. 03. 2017.
 */

public class Tab2_storitev extends Fragment{
    public Button gumb_dodaj, brisanje_vse;
    public EditText Text_podatek;
    public ListView listView_podatki;
    public ArrayList<String> seznam_podatki;
    public Baza baza;
    public ArrayAdapter<String> arrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storitev, container, false);
        gumb_dodaj = (Button) view.findViewById(R.id.button_dodaj);
        brisanje_vse =(Button) view.findViewById(R.id.brisanje_vse_lokacija);
        Text_podatek = (EditText) view.findViewById(R.id.editText_podatek);
        listView_podatki = (ListView) view.findViewById(R.id.list_view);

        OSVEZI_SEZNAM();

        gumb_dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Text_podatek.getText().length() > 0 && Text_podatek.getText().length() < 25)
                {
                    String podatek = Text_podatek.getText().toString();
                    Baza baza = new Baza(getActivity());
                    podatek = podatek.toUpperCase();
                    baza.INSERT_OPIS_STORITVE(podatek);
                    Text_podatek.setText("");
                    baza.close();
                    OSVEZI_SEZNAM();
                    Toast.makeText(getActivity(),"Nova lokacija-> " +podatek+" je dodana!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),"Ni podatka ali pa je vnos daljši od 25 znakov!!",Toast.LENGTH_LONG).show();
                }
            }///konec klik
        });

        brisanje_vse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()).setTitle("Brisanje baze Lokacij!!").setMessage("Si prepričan, da želiš pobrisati vse vnose?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (0 < baza.VELIKOST_BAZE_OPIS_STORITEV()){
                            baza = new Baza(getActivity());
                            baza.BRISANJA_VSEH_VNOSOV_OPIS_STORITVE();
                            seznam_podatki.clear();
                            arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vrstica_simple, R.id.textView_custom_tab, seznam_podatki);
                            arrayAdapter.clear();
                            OSVEZI_SEZNAM();
                            Toast.makeText(getActivity(),"Seznam pobrisan!!",Toast.LENGTH_SHORT).show();
                            baza.close();
                        }
                        else {
                            Toast.makeText(getActivity(), "V bazi ni vnosov za brisanje!", Toast.LENGTH_LONG).show();
                            baza.close();
                        }

                    }
                        }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        // Toast.makeText(getBaseContext(), "NE", Toast.LENGTH_LONG).show();
                    }}).setIcon(android.R.drawable.ic_dialog_alert).show();
                }
          });



        return view;
    }


    public void OSVEZI_SEZNAM(){
        baza = new Baza(getActivity());
        seznam_podatki = new ArrayList<String>();
        seznam_podatki = baza.VRNE_SEZNAM_OPIS_STORITVE_TIP_STRING();
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vrstica_simple, R.id.textView_custom_tab, seznam_podatki);
        listView_podatki.setAdapter(arrayAdapter);
        baza.close();
    }









}////
