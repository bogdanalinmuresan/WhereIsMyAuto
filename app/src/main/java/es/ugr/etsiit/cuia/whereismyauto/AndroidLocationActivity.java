package es.ugr.etsiit.cuia.whereismyauto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.ContentValues;

public class AndroidLocationActivity extends ActionBarActivity {

    Button btnGPSShowLocation;
    Button btnNWShowLocation;

    AppLocationService appLocationService;

    private BDWhereIsMyAuto bdwhereismyauto;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_location);
        appLocationService = new AppLocationService(
                AndroidLocationActivity.this);

        btnGPSShowLocation = (Button) findViewById(R.id.btnGPSShowLocation);
        btnGPSShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location gpsLocation = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                if (gpsLocation != null) {
                    double latitude = gpsLocation.getLatitude();
                    double longitude = gpsLocation.getLongitude();
                    double altitud = gpsLocation.getAltitude();
                    /*Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (GPS): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude
                                    + "\nAltitud: " + altitud,
                            Toast.LENGTH_SHORT).show();*/

                    /*bdwhereismyauto = new BDWhereIsMyAuto(getApplicationContext(), "DBLocalizaciones", null, 1);
                    db = bdwhereismyauto.getWritableDatabase();

                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("nombre", "Prueba");
                    nuevoRegistro.put("categoria","Prueba");
                    nuevoRegistro.put("latitud",latitude);
                    nuevoRegistro.put("longitud",longitude);
                    nuevoRegistro.put("altitud",altitud);

                    db.insert("Localizaciones", null, nuevoRegistro);*/
                } else {
                    showSettingsAlert("GPS");
                }

            }
        });

        btnNWShowLocation = (Button) findViewById(R.id.btnNWShowLocation);
        btnNWShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Location nwLocation = appLocationService
                        .getLocation(LocationManager.NETWORK_PROVIDER);

                if (nwLocation != null) {
                    double latitude = nwLocation.getLatitude();
                    double longitude = nwLocation.getLongitude();
                    double altitud = nwLocation.getAltitude();
                    /*Toast.makeText(
                            getApplicationContext(),
                            "Mobile Location (NW): \nLatitude: " + latitude
                                    + "\nLongitude: " + longitude
                                    + "\nAltitud: " + altitud,
                            Toast.LENGTH_SHORT).show();*/
                } else {
                    showSettingsAlert("NETWORK");
                }

            }
        });

    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                AndroidLocationActivity.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        AndroidLocationActivity.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }


}
