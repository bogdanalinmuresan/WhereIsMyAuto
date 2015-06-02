package es.ugr.etsiit.cuia.whereismyauto;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

import java.io.IOException;

public class ARcamActivity extends Activity {

    ArchitectView architectView;

    LocationManager locationManager;
    double latitude = 0;
    double longitude = 0;
    double altitude=0;
    float accuracy=0;

    String nombre_objetivo="";
    float id_objetivo=1;
    float latitude_objetivo=0;
    float longitude_objetivo=0;
    float altitude_objetivo=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcam);

        // la ruta del architectView en nuestro XML
        this.architectView = (ArchitectView) this.findViewById(R.id.architectView);
        //liciencia educación
         final StartupConfiguration config = new StartupConfiguration("Y1z5NfbLHv6sSWxZf+LuZI5WpZdlAF0OPP2xJHRc1pw2gM6wi+L74SgxIaXnSvQ+Nv2Iuna4Hk1JX4mxW64cgLiOJaV7nY8dfX44CPzb3BXMNDmLsqbpc7kCdGuoZB4zOknJKeGwhsM/QnaauCmiAM5MWUMJ8bOKa5mthActj6xTYWx0ZWRfX3x2TEMN5udCEo+OWszz4sTVInuzsxwxi5efPXscpPAXA5O52zBm1+RpdrrxGNQ0Ammyg3QCTODNEDjcpYhBIlG7ncKtIWkyTCLGuvgEw9dVJjSjOosgI2TslU/o3QV6oVHtZL+2xCdKvyCqQGxhcB0S9HxUKvU5vVxBPgv8VXuQEAFDFx3C5ii/ok8n2a+8P6EdJx87yv0zvmm7lEN51PWAi7lP3sDgGh6O3/2mz2hgqLv3G5AsUXOg2qJhmBqmGN3H8XX7zsABw4YOrFN8jMy9Xph5WU2/da6Mti0lyvGMBl6oMdePg+Pr+SLfPWDqL0bACG+fft1tin/qTXywdCB48MJ9FwgLGTTE0k7tpIKb4PntvgCCV7qMg1hY4GaOvLzEfSniskIRK08M+NSn1+c/huytWVsqUBvn0nOFvalkcsViR9CZkc8a+SQllhVvy0dVK6G8UR4c8VokZMDcrPZveDzE8hfTEBpeePxUtY3uOiLkYHJB0krIyVtyeajRX4fLPnDlH/N4" /* license key */);
        //liciencia trial
        // final StartupConfiguration config = new StartupConfiguration("peiS3vj7GhSSKJv275HS9y2Mcvw4fCfI+d1N4udX0CpNwecsdv23O5LEmPl4mEjIOKRXUJPpZTZMV0ND2gYFcWYft5Ozy16mzLVKkiqYeSNOQ+UgUPTx28dj4L2lT3VoJEq6RKGgY3swYUt9Li4iej3MlBibP/1EIp36RFe0opNTYWx0ZWRfX3QAQZYkWC9r5WsRhbyLMcP9l6lM7/QubkSJuC/Y0wq1cMcDR7tIJVoc//8zbZB0SWGvizmSvehAllFUdzlgrT8mWrU/gZo9PBBjVarZP6FKyfb3VunnSDEwULtNAUzfHxY1XyP9tbxxMFZZq/SOTWCM3stUXh3FQaI5vf5Wq/g+cbIWMWdcuIuZJIdswSFtXQq6MTdOilgAywykM5sZVcxWv1g5q3+afVvsvUxOlYJmDScmNnZt2lxsGd+Q2lXbwZX0apBFF3UhU/uwzLk65I++Em8Xfu1gKOBYboOTz6ajnr/Ab4Y1a0kxweLPre5HGfjeFrDKfotLdZWy7JstdIGFZteuYkovAMr3hGwJ4gN0vY0lTZzvaTfFq43HTOHZuGN3f99bhNx67Oft1mIwA5+W+UVtFYFbjgBUPXcXlxA5k610FrDaX7a5qWuZobuar0uBXqJT/R3GQr3mJBhb/aD7klC0qf+B1SOTp8/4qAhDRdZrOwN15rk=" /* license key */);
        this.architectView.onCreate(config);

        //recibir datos de la localizacion
        Intent intent=this.getIntent();
        nombre_objetivo=intent.getStringExtra("nombre");
        latitude_objetivo=intent.getFloatExtra("latitude",0);
        longitude_objetivo=intent.getFloatExtra("longitude",0);
        altitude_objetivo=intent.getFloatExtra("altitude",0);

        // actualziamos las variables latitude y longitude de la aplicacion
        getLocalization();
    }

	/*
	 * Ciclo de vida en nuestra activida
	 */

    @Override
    protected void onResume() {
        super.onResume();
        if (this.architectView != null) {
            this.architectView.setLocation(latitude, longitude,altitude, 1f);
            this.architectView.onResume();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.architectView != null) {
            this.architectView.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.architectView != null) {
            this.architectView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.architectView != null) {
            this.architectView.onLowMemory();
        }
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // IMPORTANTE cargamos el ARchitect worlds (codigo web: HTML CSS
        // javaScript)
        this.architectView.onPostCreate();
        try {
            this.architectView.load("base/index.html");
            //this.architectView.callJavascript("crearPoi('<strong>" + nombre_objetivo + longitude_objetivo + latitude_objetivo + altitude_objetivo + "</strong>');");
            // this.architectView.callJavascript("World.crearPoi('" + nombre_objetivo + +latitude_objetivo+ +longitude_objetivo+ +altitude_objetivo+"');");
            //this.architectView.callJavascript("World.crearPoi_prueba(' "+ nombre_objetivo + +longitude_objetivo+  "')");
            this.architectView.callJavascript("World.crearPoi_title('"+nombre_objetivo +"')");
            this.architectView.callJavascript("World.crearPoi_longitude(' "+ longitude_objetivo +  "')");
            this.architectView.callJavascript("World.crearPoi_latitude(' "+ latitude_objetivo +  "')");
            this.architectView.callJavascript("World.crearPoi_altitude(' "+ altitude_objetivo +  "')");
            this.architectView.callJavascript("World.crearPoi_id('"+id_objetivo+"')");
            //this.architectView.callJavascript("World.crearPoi_descripcion(' "+ nombre_objetivo +  "')");
            this.architectView.callJavascript("World.crearPoi()");

            this.architectView.onResume();
        } catch (IOException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_arcam, menu);
        return true;
    }

    private void getLocalization() {

        final Activity activity = this;

        // Obtenemos una referencia al LocationManager
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Nos registramos para recibir actualizaciones de la posición
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    altitude=location.getAltitude();
                }

                //Random rand = new Random();

               // int randomNum = rand.nextInt(50);
/*
                Toast.makeText(
                        activity,
                        "nueva posicion obtenida " + latitude + " " + longitude + " "
                                + altitude, Toast.LENGTH_SHORT).show();
*/
                architectView.setLocation(latitude, longitude,altitude, 1f);
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(activity, "Debe de habilitar el \"Acceso a su ubicación\" ",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {

                // muestro un mensaje segun el estatus erroneo de la senal
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(
                                activity,
                                " GPS y/o redes teléfonicas. Fuera de servicio",
                                Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(
                                activity,
                                "Se a perdido temporalmente la comunicación con el GPS y/o redes teléfonicas.",
                                Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.AVAILABLE:
                        //si esta disponible no hace falta informar
                       /* Toast.makeText(
                                activity,
                                "Se a establecido la conexión con el GPS y/o redes teléfonicas.",
                                Toast.LENGTH_LONG).show();
                                */
                        break;
                }

            }
        };

        // Si esta habilitada el GPS en el dispositivo
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            // obtengo la ultima localizacion del usuario, si no la hay, es null
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude=location.getAltitude();
            }

            // actualizo la posicion del usuario cada 5 seg = 5000 ms
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        } else if (locationManager // Puntos Wifi o senal telefonica
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            // obtengo la última localizacion del usuario, si no la hay, es null
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude=location.getAltitude();
            }

            // actualizo la posicion del usuario cada 5 seg = 5000 ms
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000, 0,locationListener);

        } else {
            // servicio desactivado
            Toast.makeText(
                    this,
                    "Por favor active el \"Acceso a su ubicación\" desde las configuraciones.",
                    Toast.LENGTH_LONG).show();

        }

    }

}