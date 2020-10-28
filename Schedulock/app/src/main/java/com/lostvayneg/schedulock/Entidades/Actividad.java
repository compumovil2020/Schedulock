package com.lostvayneg.schedulock.Entidades;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Actividad implements Serializable {

    private String nombre;
    private String categoria;
    private Date inicio;
    private Date fin;
    private String descripcion;
    private String prioridad;
    private String recordatorio;
    private String frencuenciaR;
    private Localizacion localizacion;
    private List<String> colaboradores;

    public Actividad() {
    }

    /*public Actividad(String nombre, String categoria, Date inicio, Date fin,String descripcion,String prioridad, String recordatorio,String frencuenciaR,LatLng localizacion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.inicio = inicio;
        this.fin = fin;
        this.descripcion=descripcion;
        this.prioridad=prioridad;
        this.recordatorio=recordatorio;
        this.frencuenciaR=frencuenciaR;
        this.localizacion = localizacion;
    }*/


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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getFrencuenciaR() {
        return frencuenciaR;
    }

    public void setFrencuenciaR(String frencuenciaR) {
        this.frencuenciaR = frencuenciaR;
    }

    public List<String> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<String> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public Localizacion getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(Localizacion localizacion) {
        this.localizacion = localizacion;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", descripcion='" + descripcion + '\'' +
                ", prioridad='" + prioridad + '\'' +
                ", recordatorio='" + recordatorio + '\'' +
                ", frencuenciaR='" + frencuenciaR + '\'' +
                ", localizacion=" + localizacion +
                ", colaboradores=" + colaboradores +
                '}';
    }

    public String toStringFechaInicio(){
        return (inicio.getYear() + "/" + inicio.getMonth() +"/" +inicio.getDate()+" "+inicio.getHours()+":"+inicio.getMinutes());
    }
    public String toStringFechaFin(){
        return (fin.getYear() + "/" + fin.getMonth() +"/" +fin.getDate()+" "+fin.getHours()+":"+fin.getMinutes());
    }
}
