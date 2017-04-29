package com.project.spaceapps.beebox.beebox.model;

/**
 * Created by Matheus on 29/04/2017.
 */

public class Place {

    private long latitude;
    private long longitude;
    private String city;
    private String uf;
    private String description;

    public Place(long latitude, long longitude, String city, String uf, String description){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.uf = uf;
        this.description = description;
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

    public String getCidade() {
        return city;
    }

    public void setCidade(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getDescricao() {
        return description;
    }

    public void setDescricao(String description) {
        this.description = description;
    }
}
