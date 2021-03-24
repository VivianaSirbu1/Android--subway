package com.example.transport;

public class DetaliiMetrou {
    public String id;
    public String denumire;
    public String nr_calatorii;
    public String peroane_uz;

    public DetaliiMetrou(String id, String denumire, String nr_calatorii, String peroane_uz) {
        this.id = id;
        this.denumire = denumire;
        this.nr_calatorii = nr_calatorii;
        this.peroane_uz = peroane_uz;
    }

    public DetaliiMetrou() {

    }

    public String getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getNr_calatorii() {
        return nr_calatorii;
    }

    public String getPeroane_uz() {
        return peroane_uz;
    }
}
