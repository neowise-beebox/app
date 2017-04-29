package com.project.spaceapps.beebox.beebox.handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Place;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matheus on 29/04/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbBeebox";

    private static final String TABLE_BEE = "Bee";

    private static final String KEY_COD = "cod";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_DATE = "date";
    private static final String KEY_PICTURE = "picture";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_SPECIES = "species";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_BEE + "("
                + KEY_COD + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_LATITUDE + " TEXT,"
                + KEY_LONGITUDE + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_PICTURE + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_SPECIES + " TEXT )";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BEE);
        onCreate(db);
    }

    public void addBee(Bee bee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Type listType = new TypeToken<List<Place>>() {}.getType();

        Gson g = new Gson();
        String beeJSON = g.toJson(bee.getPlaces());

        //values.put(KEY_COD, bee.getCod());
        values.put(KEY_LATITUDE, bee.getLatitude());
        values.put(KEY_LONGITUDE, bee.getLongitude());
        values.put(KEY_DATE, bee.getDate().toString());
        values.put(KEY_PICTURE, bee.getPicture());
        values.put(KEY_DESCRIPTION, bee.getDescription());
        values.put(KEY_SPECIES, bee.getSpecies());

        db.insert(TABLE_BEE, null, values);
        db.close();
    }

    public Bee getBee(int cod) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BEE, new String[]{KEY_COD,
                        KEY_LATITUDE,
                        KEY_LONGITUDE,
                        KEY_DATE,
                        KEY_PICTURE,
                        KEY_DESCRIPTION,
                        KEY_SPECIES}, KEY_COD + "=?",
                new String[]{String.valueOf(cod)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Bee produto = new Bee(Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1).toString()), Double.parseDouble(cursor.getString(2).toString()), (cursor.getString(3).toString()), cursor.getString(4).toString(), cursor.getString(5).toString(), cursor.getString(6).toString());

        return produto;
    }

    public List<Bee> getAllBee() {
        List<Bee> beeList = new ArrayList<Bee>();

        String selectQuery = "SELECT  * FROM " + TABLE_BEE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Bee bee = new Bee();


                bee.setCod(Integer.parseInt(cursor.getString(0)));
                bee.setLatitude(Double.parseDouble(cursor.getString(1)));
                bee.setLongitude(Double.parseDouble(cursor.getString(2)));
                bee.setDate(cursor.getString(3).toString());
                bee.setPicture(cursor.getString(4).toString());
                bee.setDescription(cursor.getString(5).toString());
                bee.setSpecies(cursor.getString(6).toString());

                beeList.add(bee);
            } while (cursor.moveToNext());
        }

        return beeList;
    }
}
