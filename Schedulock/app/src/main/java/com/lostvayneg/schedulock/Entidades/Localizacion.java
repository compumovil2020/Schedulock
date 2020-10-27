package com.lostvayneg.schedulock.Entidades;

public class Localizacion {
    private double latitud;
    private double longitud;

    public Localizacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Localizacion() {
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
