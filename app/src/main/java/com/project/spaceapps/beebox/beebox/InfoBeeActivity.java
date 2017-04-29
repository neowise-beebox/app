package com.project.spaceapps.beebox.beebox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.model.Bee;

public class InfoBeeActivity extends AppCompatActivity {

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bee);


        Intent i = getIntent();
        String id = i.getStringExtra("cod");

        db = new DatabaseHandler(this);

        Bee bee = db.getBee(Integer.parseInt(id));

        Log.d("Descrição", bee.getDescription());
    }
}
