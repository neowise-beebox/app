package com.project.spaceapps.beebox.beebox.model;

import android.provider.Settings;

import java.util.ArrayList;
import java.util.Date;

import static java.security.AccessController.getContext;

/**
 * Created by Matheus on 29/04/2017.
 */

public class Bee {

    private int cod;
    private double latitude;
    private double longitude;
    private ArrayList<Place> places;
    private String date;
    private String picture;
    private String description;
    private String species;
    private String idDevice;
    public Bee(){

    }

    public Bee(double latitude, double longitude, ArrayList<Place> places, String date, String picture, String description, String species) {
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.places =  places;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
    }

    public Bee(double latitude, double longitude,  String date, String picture, String description, String species, String idDevice) {
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
        this.idDevice =  idDevice;
    }

    public Bee(double latitude, double longitude,  String date, String picture, String description, String species, String idDevice, int cod) {
        this.cod =  cod;
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
        this.idDevice =  idDevice;
    }


    public Bee(double latitude, double longitude,  String date, String picture, String description, String species) {
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
    }

    public Bee(int cod, double latitude, double longitude, String date, String picture, String description, String species) {
        this.cod =  cod;
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
    }

    public Bee(int cod, double latitude, double longitude, ArrayList<Place> places, String date, String picture, String description, String species) {
        this.cod =  cod;
        this.latitude =  latitude;
        this.longitude =  longitude;
        this.places =  places;
        this.date =  date;
        this.picture =  picture;
        this.description =  description;
        this.species =  species;
    }

    public Bee(int cod, String description){
        this.cod =  cod;
        this.description =  description;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public ArrayList<Place> getPlaces() {
        return places;
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
