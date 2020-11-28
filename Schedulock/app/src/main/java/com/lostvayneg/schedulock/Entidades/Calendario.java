package com.lostvayneg.schedulock.Entidades;

import java.io.Serializable;

public class Calendario implements Serializable {

    private String nombre;
    private String userId;
    private String idCalendario;

    private String etiqueta;


    public Calendario() {
    }

    public Calendario(String nombre, String etiqueta) {
        this.nombre = nombre;
        this.etiqueta=etiqueta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getIdCalendario() {
        return idCalendario;
    }

    public void setIdCalendario(String idCalendario) {
        this.idCalendario = idCalendario;
    }


}