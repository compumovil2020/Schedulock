package com.lostvayneg.schedulock.Entidades;

public class Logro {
    public Logro(String titulo, String descripcion, long puntos, boolean completado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.puntos = puntos;
        this.completado = completado;
    }

    private String titulo;
    private String descripcion;
    private long puntos;
    private boolean completado;

    public Logro() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getPuntos() {
        return puntos;
    }

    public void setExpPremio(long expPremio) {
        this.puntos = puntos;
    }

    public boolean isEstaCompletado() {
        return completado;
    }

    public void setEstaCompletado(boolean estaCompletado) {
        this.completado = estaCompletado;
    }
}
