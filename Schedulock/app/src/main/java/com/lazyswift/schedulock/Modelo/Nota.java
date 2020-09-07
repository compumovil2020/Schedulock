package com.lazyswift.schedulock.Modelo;

public class Nota
{
    public String nombre;
    public Actividad actividad;

    public Nota(String nombre) {
        this.nombre = nombre;
        actividad = null;
    }

    public Nota(String nombre, Actividad actividad) {
        this.nombre = nombre;
        this.actividad = actividad;
    }
}
