package com.example.transport;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;



@Entity(tableName = "metrou")
public class Metrou {
    @PrimaryKey(autoGenerate = true)

    private int id;
    private String ruta;
    private String stplecare;
    private String stsosire;


    public  Metrou()
    {

    }

    @Ignore
    public Metrou(String ruta,String stpleacre, String stsosire) {
        this.ruta = ruta;
        this.stplecare= stpleacre;
        this.stsosire=stsosire;
    }

    public String getStplecare() {
        return stplecare;
    }

    public void setStplecare(String stplecare) {
        this.stplecare = stplecare;
    }

    public String getStsosire() {
        return stsosire;
    }

    public void setStsosire(String stsosire) {
        this.stsosire = stsosire;
    }

    public int  getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }


    @Override
    public String toString() {
        return "Metrou{" +
                "id=" + id +
                ", ruta='" + ruta + '\'' +
                ", stplecare='" + stplecare + '\'' +
                ", stsosire='" + stsosire + '\'' +
                '}';
    }
}