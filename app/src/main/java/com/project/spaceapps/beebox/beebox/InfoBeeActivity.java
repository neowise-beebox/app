package com.project.spaceapps.beebox.beebox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.spaceapps.beebox.beebox.adapter.BeeCustomAdapter;
import com.project.spaceapps.beebox.beebox.adapter.PlaceCustomAdapter;
import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Place;

import java.util.ArrayList;
import java.util.UUID;

public class InfoBeeActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ImageView iv_bee;
    private TextView tv_description;
    private TextView tv_date;
    private ListView lvPlaces;

    private TextView tv_day;
    private TextView tv_month;
    private TextView tv_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bee);

        Intent i = getIntent();
        String cod = i.getStringExtra("cod");

        db = new DatabaseHandler(this);

        Bee bee = db.getBee(Integer.parseInt(cod));

        iv_bee = (ImageView) findViewById(R.id.iv_bee);
        Bitmap bmImg = BitmapFactory.decodeFile(bee.getPicture());
        iv_bee.setImageBitmap(bmImg);

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_description = (TextView) findViewById(R.id.tv_description);

        tv_description.setText("" + bee.getDescription());
        tv_date.setText("" + bee.getDate());

        String day = bee.getDate();
        day.substring(0,2);

        String month = bee.getDate();
        month.substring(2,4);

        String year = bee.getDate();
        year.substring(4,8);


        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_year= (TextView) findViewById(R.id.tv_year);

        tv_month.setText("" + month);
        tv_day.setText("" + day);
        tv_year.setText("" + year);
        

        lvPlaces = (ListView) findViewById(R.id.lvPlaces);

        ArrayList<Place> places = new ArrayList<Place>();
        places.add(new Place("São Paulo", "São Paulo - Capital"));
        places.add(new Place("Guarulhos", "GRU"));
        places.add(new Place("São Paulo", "São Paulo - Capital"));
        places.add(new Place("São Paulo", "São Paulo - Capital"));
        places.add(new Place("São Paulo", "São Paulo - Capital"));

        PlaceCustomAdapter placeCustomAdapter;
        placeCustomAdapter = new PlaceCustomAdapter(places, this);

        lvPlaces.setAdapter(placeCustomAdapter);

    }
}
