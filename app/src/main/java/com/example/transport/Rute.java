package com.example.transport;

import java.io.Serializable;
import java.util.Date;

public class Rute implements Serializable {
    private String statie_plecare;
    private String statie_sosire;
    private Date data;

    public Rute(String statie_plecare, String statie_sosire, Date data) {
        this.statie_plecare = statie_plecare;
        this.statie_sosire = statie_sosire;
        this.data = data;
    }

    public String getStatie_plecare() {
        return statie_plecare;
    }

    public void setStatie_plecare(String statie_plecare) {
        this.statie_plecare = statie_plecare;
    }

    public String getStatie_sosire() {
        return statie_sosire;
    }

    public void setStatie_sosire(String statie_sosire) {
        this.statie_sosire = statie_sosire;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "statie_plecare='" + statie_plecare + '\'' +
                ", statie_sosire='" + statie_sosire + '\'' +
                ", data=" + data +
                '}';
    }
}
