package es.ugr.etsiit.cuia.whereismyauto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by samuel on 8/4/15.
 */
public class Comunicaciones {

    protected Context context;

    public Comunicaciones (Context context) {
        this.context = context;
    }

    public boolean comprobar_si_existe(ContentValues contenido, SQLiteDatabase db){
        String[] columnas = new String[]{
                Tablas.Localizaciones._ID,
                Tablas.Localizaciones.NOMBRE,
                Tablas.Localizaciones.CATEGORIA,
                Tablas.Localizaciones.LATITUD,
                Tablas.Localizaciones.LONGITUD,
                Tablas.Localizaciones.ALTITUD
        };

        Cursor c = db.query(Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES, columnas, "nombre = '" + contenido.getAsString("nombre") + "'", null, null, null, Tablas.Localizaciones.NOMBRE);
        if(c.moveToFirst())
            return true;
        else
            return false;
    }

    public void insertar_localizacion(ContentValues contenido, SQLiteDatabase db){
        db.insert("Localizaciones", null, contenido);
    }

    public void eliminar_localizacion(ContentValues contenido, SQLiteDatabase db){
        String[] columnas = new String[]{
                Tablas.Localizaciones._ID,
                Tablas.Localizaciones.NOMBRE,
                Tablas.Localizaciones.CATEGORIA,
                Tablas.Localizaciones.LATITUD,
                Tablas.Localizaciones.LONGITUD,
                Tablas.Localizaciones.ALTITUD
        };
        //Cursor c = db.query(Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES, columnas, "nombre = '" + contenido.getAsString("nombre") + "'", null, null, null, Tablas.Localizaciones.NOMBRE);
        db.delete(Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES, "nombre = '" + contenido.getAsString("nombre") + "'",null);
        Toast.makeText( this.context,"La ubicación " + contenido.getAsString("nombre") + " ha sido eliminada correctamente", Toast.LENGTH_SHORT).show();
    }

    public void actualizar_localizacion(ContentValues contenido, SQLiteDatabase db){

    }

}
