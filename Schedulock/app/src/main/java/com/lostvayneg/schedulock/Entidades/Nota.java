package com.lostvayneg.schedulock.Entidades;

public class Nota
{
    private String nombre;
    private Actividad actividad;

    public Nota(String nombre) {
        this.nombre = nombre;
        actividad = null;
    }

    public Nota(String nombre, Actividad actividad) {
        this.nombre = nombre;
        this.actividad = actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
