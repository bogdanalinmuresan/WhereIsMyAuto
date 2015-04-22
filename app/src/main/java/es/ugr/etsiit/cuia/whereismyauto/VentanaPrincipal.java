package es.ugr.etsiit.cuia.whereismyauto;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.text.Editable;
import java.lang.String;

public class VentanaPrincipal extends ActionBarActivity {

    private BDWhereIsMyAuto bdwhereismyauto;
    private SQLiteDatabase db;

    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        appLocationService = new AppLocationService(
                VentanaPrincipal.this);

        bdwhereismyauto = new BDWhereIsMyAuto(this, "DBLocalizaciones", null, 1);
        db = bdwhereismyauto.getWritableDatabase();
    }

    @Override
    protected void onResume(){
        super.onResume();
        bdwhereismyauto = new BDWhereIsMyAuto(this, "DBLocalizaciones", null, 1);
        db = bdwhereismyauto.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ventana_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void LanzarAR(View view) {
        Intent intent = new Intent(this, ARcamActivity.class);
        startActivity(intent);
    }*/

    public void LanzarGPS (View view){
        //Intent intent =new Intent(this,AndroidLocationActivity.class);
        //startActivity(intent);

        // Mostramos un popup para introducir el nombre de la nueva localizacion
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Title");
        alert.setMessage("Message");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String value = input.getText();
                Editable value = input.getText();
                String nombre = value.toString();
                // Do something with value!

                // Cuando se acepte se almacenara la ubicacion actual
                Location gpsLocation = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);
                /*Location nwLocation = appLocationService
                        .getLocation(LocationManager.NETWORK_PROVIDER);*/

                if (gpsLocation != null) {
                    double latitude = gpsLocation.getLatitude();
                    double longitude = gpsLocation.getLongitude();
                    double altitud = gpsLocation.getAltitude();

                    bdwhereismyauto = new BDWhereIsMyAuto(getApplicationContext(), "DBLocalizaciones", null, 1);
                    db = bdwhereismyauto.getWritableDatabase();

                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("nombre", nombre);
                    nuevoRegistro.put("categoria","Vehiculo");
                    nuevoRegistro.put("latitud",latitude);
                    nuevoRegistro.put("longitud",longitude);
                    nuevoRegistro.put("altitud",altitud);

                    db.insert("Localizaciones", null, nuevoRegistro);
                }else{
                    showSettingsAlert("GPS");
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                VentanaPrincipal.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage(provider + " is not enabled! Want to go to settings menu?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        VentanaPrincipal.this.startActivity(intent);
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
