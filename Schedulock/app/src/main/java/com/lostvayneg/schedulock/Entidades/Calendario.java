package com.lostvayneg.schedulock.Entidades;

public class Calendario {

    private String nombre;
    private String etiqueta;

    public Calendario(String nombre, String etiqueta) {
        this.nombre = nombre;
        this.etiqueta = etiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}