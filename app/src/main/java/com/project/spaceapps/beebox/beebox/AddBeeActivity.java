package com.project.spaceapps.beebox.beebox;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.project.spaceapps.beebox.beebox.handler.DatabaseHandler;
import com.project.spaceapps.beebox.beebox.helper.GPSTracker;
import com.project.spaceapps.beebox.beebox.model.Bee;
import com.project.spaceapps.beebox.beebox.model.Task;
import com.project.spaceapps.beebox.beebox.webservice.APIClient;
import com.project.spaceapps.beebox.beebox.webservice.APIInterface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@RuntimePermissions
public class AddBeeActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private EditText ed_description;
    private Button btn_add_bee;
    private DatabaseHandler db;
    private GPSTracker gps;
    private String filePath = "";
    private String description = "";
    private double latitude = 0f, longitude = 0f;
    private String android_id;
    private ArrayList<Bee> bees;

    Call<Task> callBee;
    APIInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bee);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AddBeeActivityPermissionsDispatcher.getLatLngWithCheck(AddBeeActivity.this);
        ed_description = (EditText) findViewById(R.id.ed_description);
        btn_add_bee = (Button) findViewById(R.id.btn_add_bee);

        db = new DatabaseHandler(this);

        apiService = APIClient.getService().create(APIInterface.class);

        android_id = Settings.Secure.getString(this.getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e("IDANDROID", "" + android_id);

        btn_add_bee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddBeeActivityPermissionsDispatcher.getLatLngWithCheck(AddBeeActivity.this);
                description = ed_description.getText().toString();

                Log.d("Descrição", "" + description.length());

                if (description.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Para adicionar uma abelha, insira uma descrição para ela!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (filePath.length() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Para identificar a abelha, selecione uma imagem.", Toast.LENGTH_LONG).show();
                    return;
                }

                Calendar cal = Calendar.getInstance();
                Date date = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                db.addBee(new Bee(latitude, longitude, sdf.format(date), filePath, description, "x"));

                apiService = APIClient.getService().create(APIInterface.class);

                bees = (ArrayList<Bee>) db.getAllBee();
                int cod = bees.size();

                callBee = apiService.saveBee(new Gson().toJson(new Bee(latitude, longitude, sdf.format(date), filePath, description, "x", android_id, cod)));

                callBee.enqueue(new Callback<Task>() {
                    @Override
                    public void onResponse(Call<Task> call, Response<Task> response) {
                        if (response.raw().code() == 200) {
                            Task t = response.body();
                            Log.e("INFOBEE", "" + response.raw().body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Task> call, Throwable t) {
                        Log.e("INFOMEMBRO", t.toString());
                    }
                });

                Log.e("INFOBEE", "ENTROU");

                finish();

            }
        });

        this.imageView = (ImageView) this.findViewById(R.id.iv_bee);
        Button photoButton = (Button) this.findViewById(R.id.btn_add_image);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddBeeActivityPermissionsDispatcher.takePhotoWithCheck(AddBeeActivity.this);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            AddBeeActivityPermissionsDispatcher.saveWithCheck(AddBeeActivity.this, data);
        }
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void save(Intent data){
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(photo);

        Uri tempUri = getImageUri(getApplicationContext(), photo);
        File finalFile = new File(getRealPathFromURI(tempUri));

        filePath = finalFile.getPath();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    public void takePhoto() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AddBeeActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void getLatLng(){
        gps = new GPSTracker(AddBeeActivity.this);
        latitude = gps.getLatitude();
        longitude = gps.getLongitude();
    }
}
