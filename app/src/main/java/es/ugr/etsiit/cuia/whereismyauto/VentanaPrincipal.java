package es.ugr.etsiit.cuia.whereismyauto;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class VentanaPrincipal extends ActionBarActivity {

    private BDWhereIsMyAuto bdwhereismyauto;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_principal);

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

    public void LanzarAR(View view) {
        Intent intent = new Intent(this, ARcamActivity.class);
        startActivity(intent);
    }
    public void LanzarGPS (View view){
        Intent intent =new Intent(this,AndroidLocationActivity.class);
        startActivity(intent);
    }
}
