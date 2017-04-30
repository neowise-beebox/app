package com.project.spaceapps.beebox.beebox.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;


public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    private Location location;
    protected LocationManager locationManager;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    double latitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    double longitude;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    //Recupera a localização atual via APPI google maps
    public Location getLocation() {

        try {

            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled) {
                Toast.makeText(this.mContext, "Não foi possível obter sua localização, verifique se o GPS está ativo!", Toast.LENGTH_LONG).show();
                showSettingsAlert();
                return location;
            }

            if (!isNetworkEnabled) {
                Toast.makeText(this.mContext, "Verifique sua conexão com a Internet!", Toast.LENGTH_LONG).show();
                return location;
            }

            canGetLocation = true;

            // First get location from Network Provider
            if (isNetworkEnabled) {

                location = getLocale(locationManager);

                /*
                if (ContextCompat.checkSelfPermission(this.mContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) this.mContext,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                        location = getLocale(locationManager);

                    } else {

                        //Solicita permissão para uso do GPS
                        ActivityCompat.requestPermissions((Activity) this.mContext,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                        this.canGetLocation = false;
                    }
                }else{
                    location = getLocale(locationManager);
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;

    }


    private Location getLocale(LocationManager locationManager) {

        try {

            if (ActivityCompat
                    .checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                canGetLocation = false;

                //Solicita permissão para uso do GPS
                ActivityCompat.requestPermissions((Activity) this.mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            } else {

                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("Network", "Network");


                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location != null) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }

                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                return location;

            }


        } catch (Exception ex) {

            ex.printStackTrace();

        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude() {
        return location != null ? location.getLatitude() : latitude;
    }

    public double getLongitude() {
        return location != null ? location.getLongitude() : longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }


    public void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Configrações de GPS");
        alertDialog.setMessage("GPS não está ativo. Deseja ativar agora?");

        alertDialog.setPositiveButton("Configurações", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });


        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    public void showSettingsGps() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Permissão para uso de GPS");
        alertDialog.setMessage("Não foi possível recuperar sua localização, deseja permitir a captura de sua localização?");

        alertDialog.setPositiveButton("Ativar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Solicita permissão para uso do GPS
                ActivityCompat.requestPermissions((Activity) mContext,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                dialog.cancel();

            }
        });


        alertDialog.setNegativeButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}