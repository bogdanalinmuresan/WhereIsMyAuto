package es.ugr.etsiit.cuia.whereismyauto;

/**
 * Created by samuel on 8/4/15.
 */
public class Localizacion {
    private String nombre;
    private String categoria;
    private float latitud;
    private float longitud;
    private float altitud;

    public Localizacion(){

    }

    public Localizacion(String nombre, String categoria, float latitud, float longitud, float altitud){
        this.nombre = nombre;
        this.categoria = categoria;
        this.latitud = latitud;
        this.longitud = longitud;
        this.altitud = altitud;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getCategoria(){
        return this.categoria;
    }

    public float getLatitud(){
        return this.latitud;
    }

    public float getLongitud(){
        return this.longitud;
    }

    public float getAltitud(){
        return this.altitud;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setCategoria(String categoria){
        this.categoria = categoria;
    }

    public void setLatitud(float latitud){
        this.latitud = latitud;
    }

    public void setLongitud(float longitud){
        this.longitud = longitud;
    }

    public void setAltitud(float altitud){
        this.altitud = altitud;
    }

}
