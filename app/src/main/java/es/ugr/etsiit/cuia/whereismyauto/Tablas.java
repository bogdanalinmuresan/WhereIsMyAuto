package es.ugr.etsiit.cuia.whereismyauto;

import android.provider.BaseColumns;

/**
 * Created by samuel on 8/4/15.
 */
public class Tablas {

    public static final class Localizaciones implements BaseColumns {
        private Localizaciones(){};
        public static final String NOMBRE_TABLA_LOCALIZACIONES = "Localizaciones";
        public static final String _ID = "_id";
        public static final String NOMBRE = "nombre";
        public static final String CATEGORIA = "categoria";
        public static final String LATITUD = "latitud";
        public static final String LONGITUD = "longitud";
        public static final String ALTITUD = "altitud";
    }
}
