package com.lostvayneg.schedulock.Entidades;

public class Actividad {

    private String nombre;
    private String categoria;
    private String inicio;
    private String fin;

    public Actividad(String nombre, String categoria, String inicio, String fin) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.inicio = inicio;
        this.fin = fin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
