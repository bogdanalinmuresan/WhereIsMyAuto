package es.ugr.etsiit.cuia.whereismyauto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samuel on 8/4/15.
 */
public class BDWhereIsMyAuto extends SQLiteOpenHelper {

    // Sentencias SQL para crear las tablas.
    String sqlCreateTablaLocalizaciones =	"CREATE TABLE " + Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES + "(" +
            Tablas.Localizaciones._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Tablas.Localizaciones.NOMBRE + " TEXT, " +
            Tablas.Localizaciones.CATEGORIA + " BOOL," +
            Tablas.Localizaciones.LATITUD + " FLOAT," +
            Tablas.Localizaciones.LONGITUD + " FLOAT," +
            Tablas.Localizaciones.ALTITUD + " FLOAT" +
            ")";

    public BDWhereIsMyAuto(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreateTablaLocalizaciones);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aqu� utilizo directamente la opci�n de
        //      eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        //      Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este m�todo deber�a ser m�s elaborado.

        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + Tablas.Localizaciones.NOMBRE_TABLA_LOCALIZACIONES);

        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreateTablaLocalizaciones);
    }


}
