package com.jurext.belezenje_storitve.belezenje_storitev;

/**
 * Created by Jure_Lokovsek on 28. 12. 2016.
 */

public class Storitev {
    int id;
    String storitev, stranka, kraj, cas_trajanja, datum_zacetek, datum_konec, cas_zacetek, cas_konec, TAGE_zahtevnost_dela;

    public Storitev(String storitev, String stranka, String kraj, String cas_trajanja, String datum_zacetek, String datum_konec, String cas_zacetek, String cas_konec, String TAGE_zahtevnost_dela) {
        this.storitev = storitev;
        this.stranka = stranka;
        this.kraj = kraj;
        this.cas_trajanja = cas_trajanja;
        this.datum_zacetek = datum_zacetek;
        this.datum_konec = datum_konec;
        this.cas_zacetek = cas_zacetek;
        this.cas_konec = cas_konec;
        this.TAGE_zahtevnost_dela = TAGE_zahtevnost_dela;
    }



    public Storitev() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoritev() {
        return storitev;
    }

    public void setStoritev(String storitev) {
        this.storitev = storitev;
    }

    public String getStranka() {
        return stranka;
    }

    public void setStranka(String stranka) {
        this.stranka = stranka;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getCas_trajanja() {
        return cas_trajanja;
    }

    public void setCas_trajanja(String cas_trajanja) {
        this.cas_trajanja = cas_trajanja;
    }

    public String getDatum_zacetek() {
        return datum_zacetek;
    }

    public void setDatum_zacetek(String datum_zacetek) {
        this.datum_zacetek = datum_zacetek;
    }

    public String getDatum_konec() {
        return datum_konec;
    }

    public void setDatum_konec(String datum_konec) {
        this.datum_konec = datum_konec;
    }

    public String getCas_zacetek() {
        return cas_zacetek;
    }

    public void setCas_zacetek(String cas_zacetek) {
        this.cas_zacetek = cas_zacetek;
    }

    public String getCas_konec() {
        return cas_konec;
    }

    public void setCas_konec(String cas_konec) {
        this.cas_konec = cas_konec;
    }

    public String getTAGE_zahtevnost_dela() {
        return TAGE_zahtevnost_dela;
    }

    public void setTAGE_zahtevnost_dela(String TAGE_zahtevnost_dela) {
        this.TAGE_zahtevnost_dela = TAGE_zahtevnost_dela;
    }
}
