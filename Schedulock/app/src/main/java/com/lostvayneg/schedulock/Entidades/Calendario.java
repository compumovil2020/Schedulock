package com.lostvayneg.schedulock.Entidades;

public class Calendario {

    private String nombre;

    public Calendario() {
    }

    public Calendario(String nombre, String etiqueta) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}