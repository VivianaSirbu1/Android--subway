package com.example.transport;


public class StatieMetrou {
    private String statie;
    private int km;

    public StatieMetrou(String statie, int km) {
        this.statie = statie;
        this.km = km;
    }

    public String getStatie() {
        return statie;
    }

    public int getKm() {
        return km;
    }

    public void setStatie(String statie) {
        this.statie = statie;
    }

    public void setKm(int km) {
        this.km = km;
    }

    @Override
    public String toString() {
        return "StatieMetrou{" +
                "statie='" + statie + '\'' +
                ", km=" + km +
                '}';
    }
}