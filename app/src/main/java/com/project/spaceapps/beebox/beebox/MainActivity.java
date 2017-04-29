package com.project.spaceapps.beebox.beebox;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.spaceapps.beebox.beebox.adapter.BeeCustomAdapter;
import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.model.Bee;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i = new Intent(MainActivity.this, AddBeeActivity.class);
                startActivity(i);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        //seta o herder do menu lateral
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        View navHeaderView = navView.inflateHeaderView(R.layout.nav_header_main);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    private GoogleMap mMap;
    private ArrayList<Bee> bees;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        for (Bee bee : bees) {
            LatLng latLng = new LatLng(bee.getLatitude(), bee.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(bee.getDescription()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

    }

    DatabaseHandler db;

    @Override
    protected void onResume() {
        super.onResume();
        db = new DatabaseHandler(this);

        ListView listViewProduto;
        listViewProduto = (ListView) findViewById(R.id.list_bee);

        bees = (ArrayList<Bee>) db.getAllBee();

        BeeCustomAdapter beeCustomAdapter;
        beeCustomAdapter = new BeeCustomAdapter(bees, this);

        listViewProduto.setAdapter(beeCustomAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
