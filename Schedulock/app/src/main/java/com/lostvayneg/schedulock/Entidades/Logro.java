package com.lostvayneg.schedulock.Entidades;

public class Logro {
    private String titulo;
    private String descripcion;
    private long expPremio;
    private boolean estaCompletado;

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

    public long getExpPremio() {
        return expPremio;
    }

    public void setExpPremio(long expPremio) {
        this.expPremio = expPremio;
    }

    public boolean isEstaCompletado() {
        return estaCompletado;
    }

    public void setEstaCompletado(boolean estaCompletado) {
        this.estaCompletado = estaCompletado;
    }
}
