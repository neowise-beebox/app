package com.project.spaceapps.beebox.beebox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.spaceapps.beebox.beebox.adapter.PlaceCustomAdapter;
import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Place;
import com.project.spaceapps.beebox.beebox.model.RankingPlace;
import com.project.spaceapps.beebox.beebox.webservice.APIClient;
import com.project.spaceapps.beebox.beebox.webservice.APIInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoBeeActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ImageView iv_bee;
    private TextView tv_description;
    private ListView lvPlaces;

    private TextView tv_day;
    private TextView tv_month;
    private TextView tv_year;

    private String android_id;
    public ArrayList<Place> placeList;

    Call<ArrayList<RankingPlace>> call;
    PlaceCustomAdapter placeCustomAdapter;
    APIInterface apiService;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_bee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progress = ProgressDialog.show(this, "Carregando", "Buscando informações", true);

        Intent i = getIntent();
        String cod = i.getStringExtra("cod");

        db = new DatabaseHandler(this);

        Bee bee = db.getBee(Integer.parseInt(cod));

        iv_bee = (ImageView) findViewById(R.id.iv_bee);
        Bitmap bmImg = BitmapFactory.decodeFile(bee.getPicture());
        iv_bee.setImageBitmap(bmImg);

        tv_description = (TextView) findViewById(R.id.tv_description);

        tv_description.setText("" + bee.getDescription());

        String day = bee.getDate();
        day = day.substring(0, 2);

        String month = bee.getDate();
        month = month.substring(3, 5);

        String year = bee.getDate();
        year = year.substring(6, 10);

        tv_month = (TextView) findViewById(R.id.tv_month);
        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_year = (TextView) findViewById(R.id.tv_year);

        tv_month.setText("" + month);
        tv_day.setText("" + day);
        tv_year.setText("" + year);

        lvPlaces = (ListView) findViewById(R.id.lvPlaces);

        android_id = Settings.Secure.getString(this.getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        apiService = APIClient.getService().create(APIInterface.class);
        call = apiService.getBeeByID(android_id, Integer.parseInt(cod));
        placeList = new ArrayList<>();

        call.enqueue(new Callback<ArrayList<RankingPlace>>() {
            @Override
            public void onResponse(Call<ArrayList<RankingPlace>> call, Response<ArrayList<RankingPlace>> response) {
                List<RankingPlace> l = new ArrayList<RankingPlace>();
                l.addAll(response.body());

                for (RankingPlace rankingPlace : l) {
                    placeList.add(new Place(rankingPlace.getName(), "" + rankingPlace.getScore()));
                }

                Collections.reverse(placeList);

                placeCustomAdapter = new PlaceCustomAdapter(placeList, getApplicationContext());
                lvPlaces.setAdapter(placeCustomAdapter);

                progress.dismiss();
            }

            @Override
            public void onFailure(Call<ArrayList<RankingPlace>> call, Throwable t) {
                Log.e("INFOBEE", t.toString());
                progress.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
