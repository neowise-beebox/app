package com.project.spaceapps.beebox.beebox;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.model.Bee;

public class InfoBeeActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ImageView iv_bee;
    private TextView tv_description;
    private TextView tv_date;
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

    }
}
