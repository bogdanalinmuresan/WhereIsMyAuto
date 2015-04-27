package es.ugr.etsiit.cuia.whereismyauto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


public class ListaLocalizaciones extends Activity {

    private ProgressDialog progressdialog;
    private Handler progresshandler;
    private Message msg;
    private BDWhereIsMyAuto bdwhereismyauto;
    private SQLiteDatabase db;
    private Cursor datosLocalizaciones;
    private ListView listaLocalizaciones;

    private BDWhereIsMyAuto bdwhereisauto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lista_localizaciones);

        bdwhereisauto = new BDWhereIsMyAuto(this, "DBLocalizaciones", null, 1);
        db = bdwhereisauto.getWritableDatabase();
        obtenerLocalizaciones();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_localizaciones, menu);
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

    public void obtenerLocalizaciones(){
        obtenerDatosLocalizaciones();
        System.out.println(datosLocalizaciones.getCount());
        listaLocalizaciones = (ListView) findViewById(R.id.lvInventario);
        if(datosLocalizaciones != null && datosLocalizaciones.getCount() > 0)
            listaLocalizaciones.setAdapter(new MiAdaptadorInventario(ListaLocalizaciones.this));
    }

    private void EnviarMensaje(String Mensaje, int Orden){
        Message msg = new Message();
        msg.obj = (Object)Mensaje;
        msg.what = Orden;
        progresshandler.sendMessage(msg);
    }

    private class MiAdaptadorInventario extends BaseAdapter {

        private LayoutInflater mInflater;

        public MiAdaptadorInventario(Context context){
            super();
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return datosLocalizaciones.getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            datosLocalizaciones.moveToPosition(position);
            return datosLocalizaciones.getInt(datosLocalizaciones.getColumnIndex(Tablas.Localizaciones._ID));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){ //Si el layout esta vacio y el layout que queremos mostrar no es el gridView de articulos (si no el de lista de subcategorias ) lo inflamos
                //Seleccionamos el xml donde hemos definido el layout de nuestro item
                convertView = mInflater.inflate(R.layout.elemento_lista, null);
                holder = new ViewHolder();
                holder.nombre = (TextView)convertView.findViewById(R.id.tvtxtNombre);
                //holder.cpu = (TextView)convertView.findViewById(R.id.tvCpu);
                //holder.localizacion = (TextView)convertView.findViewById(R.id.tvLocalizacion);
                convertView.setTag(holder);
            }else{//Si el "layout, celda, item" ya est� inflado/creado tan solo lo llamamos
                holder = (ViewHolder) convertView.getTag();
            }

            datosLocalizaciones.moveToPosition(position);

            holder.nombre.setText(datosLocalizaciones.getString(datosLocalizaciones.getColumnIndex(Tablas.Localizaciones.NOMBRE)));
            //holder.cpu.setText(datosInventario.getString(datosInventario.getColumnIndex(Tablas.Equipos.CPU)));
            //holder.localizacion.setText(datosInventario.getString(datosInventario.getColumnIndex(Tablas.Equipos.LOCALIZACION)));

            return convertView;
        }
    }

    static class ViewHolder{
        TextView nombre;
    }

    private void obtenerDatosLocalizaciones(){

        String[] columnas = new String[]{
                Tablas.Localizaciones._ID,
                Tablas.Localizaciones.NOMBRE,
                Tablas.Localizaciones.CATEGORIA,
                Tablas.Localizaciones.ALTITUD,
                Tablas.Localizaciones.LATITUD
        };
        datosLocalizaciones = db.query(Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES, columnas, null, null, null, null, Tablas.Localizaciones._ID);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent i = new Intent();
            i.setClass(getApplicationContext(), VentanaPrincipal.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            finish();
        }
        return super.onKeyUp(keyCode, event);
    }

}

