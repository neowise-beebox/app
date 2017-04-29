package com.project.spaceapps.beebox.beebox.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Matheus on 29/04/2017.
 */

public class Bee {

    int cod;
    long latitude;
    long longitude;
    ArrayList<Place> places;
    Date date;
    String picture;
    String description;

    public Bee(int cod, long latitude, long longitude, ArrayList<Place> places, Date date, String picture, String description) {
        this.cod =  cod;
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.places =  places;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
    }

    public Bee(int cod, String description) {
        this.cod =  cod;
        this.description =  description;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public long getLatitude() {
        return latitude;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Place> getLocais() {
        return places;
    }

    public void setLocais(ArrayList<Place> places) {
        this.places = places;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFoto() {
        return picture;
    }

    public void setFoto(String picture) {
        this.picture = picture;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String description) {
        this.description = description;
    }
}
