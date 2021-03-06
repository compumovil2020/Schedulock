package com.lostvayneg.schedulock.Entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private String id;
    private String nombre;
    private String email;
    private String edad;
    private String genero;
    private int nivel;
    private long experiencia;
    private List<Calendario> calendarios;
    private List<Logro> logros;
    private List<String> idsNotas;
    private Localizacion localizacion;

    public Usuario() {
        this.idsNotas = new ArrayList<>();
        this.calendarios = new ArrayList<>();
        this.logros = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion localizacion) {
        this.localizacion = localizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public long getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(long experiencia) {
        this.experiencia = experiencia;
    }

    public List<Calendario> getCalendarios() {
        return calendarios;
    }

    public void setCalendarios(List<Calendario> calendarios) {
        this.calendarios = calendarios;
    }

    public List<Logro> getLogros() {
        return logros;
    }

    public void setLogros(List<Logro> logros) {
        this.logros = logros;
    }

    public List<String> getIdsNotas() {
        return idsNotas;
    }

    public void setIdsNotas(List<String> idsNotas) {
        this.idsNotas = idsNotas;
    }
}
