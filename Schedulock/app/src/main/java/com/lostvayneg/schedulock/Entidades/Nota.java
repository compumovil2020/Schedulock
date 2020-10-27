package com.lostvayneg.schedulock.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Nota
{
    private String nombre;
    private List<Object> adjuntos;
    private Actividad actividad;

    public Nota() {
        this.actividad = null;
        this.adjuntos = new ArrayList<>();
    }

    public Nota(String s, Actividad actividad) {
    }

    public Nota(String s) {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Object> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<Object> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
