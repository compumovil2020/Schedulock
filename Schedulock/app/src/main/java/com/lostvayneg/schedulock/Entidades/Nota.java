package com.lostvayneg.schedulock.Entidades;

import java.util.ArrayList;
import java.util.List;

public class Nota
{
    private String nombre;
    private String descripcion;
    private List<Object> adjuntos;
    private String idUsuario;
    private String idActividad;

    public Nota() {
        this.idActividad = null;
        this.adjuntos = new ArrayList<>();
    }

    public Nota(String s, String actividad) {
    }

    public Nota(String s) {
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
