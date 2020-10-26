package com.lostvayneg.schedulock.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Nota
{
    private String nombre;
    private List<Object> adjuntos;

    public Nota() {
        this.adjuntos = new ArrayList<>();
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
}
