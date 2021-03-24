package com.example.transport;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "conductori")
public class Conductor {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String nume;
    private String email;
    private String sex;
    private String dataNasterii;

    public Conductor(long id, String nume, String email, String sex, String dataNasterii) {
        this.id = id;
        this.nume = nume;
        this.email = email;
        this.sex = sex;
        this.dataNasterii = dataNasterii;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDataNasterii() {
        return dataNasterii;
    }

    public void setDataNasterii(String dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", dataNasterii='" + dataNasterii + '\'' +
                '}';
    }
}
