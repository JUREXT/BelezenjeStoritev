package com.jurext.belezenje_storitve.belezenje_storitev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_storitev extends ArrayAdapter<Storitev> {
    public Adapter_storitev(Context context, ArrayList<Storitev> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Storitev objekt = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.vrstica_moderna, parent, false);
        }

        TextView storitev=(TextView) convertView.findViewById(R.id.storitev);
        TextView stranka=(TextView) convertView.findViewById(R.id.stranka);
        TextView kraj=(TextView) convertView.findViewById(R.id.kraj);
        TextView trajanja=(TextView) convertView.findViewById(R.id.trajanje);
        TextView datum_start=(TextView) convertView.findViewById(R.id.datum_zacetek);
        TextView datum_konec=(TextView) convertView.findViewById(R.id.datum_konec);
        TextView cas_zacetek=(TextView) convertView.findViewById(R.id.ura_start);
        TextView cas_konec=(TextView) convertView.findViewById(R.id.ura_stop);
        TextView oznaka=(TextView) convertView.findViewById(R.id.oznaka);
        ///
        storitev.setText(objekt.getStoritev());
        stranka.setText(objekt.getStranka());
        kraj.setText(objekt.getKraj());
        trajanja.setText(objekt.getCas_trajanja());
        datum_start.setText(objekt.getDatum_zacetek());
        datum_konec.setText(objekt.getDatum_konec());
        cas_zacetek.setText(objekt.getCas_zacetek());
        cas_konec.setText(objekt.getCas_konec());
        oznaka.setText(objekt.getTAGE_zahtevnost_dela());


        return convertView;
    }

}
