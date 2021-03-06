package es.ugr.etsiit.cuia.whereismyauto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import java.net.Authenticator;

public class VentanaPrincipal extends Activity {

    private BDWhereIsMyAuto bdwhereismyauto;
    private SQLiteDatabase db;
    private Comunicaciones com = new Comunicaciones(this);

    AppLocationService appLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ventana_principal);



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

        alert.setTitle("Nueva localización");
        alert.setMessage("Introduce un nombre");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Recordar", new DialogInterface.OnClickListener() {
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


                    //Context context = getApplicationContext();
                    //Toast.makeText(context, "Recordar posicion" + latitude + " " + longitude + " " + altitud, Toast.LENGTH_SHORT).show();

                    bdwhereismyauto = new BDWhereIsMyAuto(getApplicationContext(), "DBLocalizaciones", null, 1);
                    db = bdwhereismyauto.getWritableDatabase();

                    ContentValues nuevoRegistro = new ContentValues();
                    nuevoRegistro.put("nombre", nombre);
                    nuevoRegistro.put("categoria","Vehiculo");
                    nuevoRegistro.put("latitud",latitude);
                    nuevoRegistro.put("longitud",longitude);
                    nuevoRegistro.put("altitud",altitud);

                    Boolean existe = com.comprobar_si_existe(nuevoRegistro,db);

                    if (!existe) {
                        com.insertar_localizacion(nuevoRegistro, db);
                    }else{
                        Toast.makeText(getApplicationContext(),"El nombre indicado ya existe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    showSettingsAlert("GPS");
                }
            }
        });

        alert.setNegativeButton("Atrás", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();
    }

    public void LanzarLista (View view){
        Intent intent = new Intent(this, ListaLocalizaciones.class);
        startActivity(intent);
        finish();
    }

    public void showSettingsAlert(String provider) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                VentanaPrincipal.this);

        alertDialog.setTitle(provider + " SETTINGS");

        alertDialog
                .setMessage("¡" + provider + " no está activado! ¿Quieres ir al menú de ajustes?");

        alertDialog.setPositiveButton("Ajustes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        VentanaPrincipal.this.startActivity(intent);
                    }
                });

        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }
}
