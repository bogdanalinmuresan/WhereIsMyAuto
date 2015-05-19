package es.ugr.etsiit.cuia.whereismyauto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by samuel on 8/4/15.
 */
public class Comunicaciones {

    protected Context context;

    public Comunicaciones (Context context) {
        this.context = context;
    }

    private boolean comprobar_si_existe(ContentValues contenido, SQLiteDatabase db){
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

    private void insertar_localizacion(ContentValues contenido, SQLiteDatabase db){

    }

    private void eliminar_localizacion(ContentValues contenido, SQLiteDatabase db){

    }

    private void actualizar_localizacion(ContentValues contenido, SQLiteDatabase db){

    }

}
