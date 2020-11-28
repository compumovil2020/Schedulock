package com.lostvayneg.schedulock.Entidades;

public class Habito extends Actividad {
    private int vecesRealizacion;
    private int diasRealizacion;
    private int progreso;
    private EnumDias dias;

    public int getVecesRealizacion() {
        return vecesRealizacion;
    }

    public void setVecesRealizacion(int vecesRealizacion) {
        this.vecesRealizacion = vecesRealizacion;
    }

    public int getDiasRealizacion() {
        return diasRealizacion;
    }

    public void setDiasRealizacion(int diasRealizacion) {
        this.diasRealizacion = diasRealizacion;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public EnumDias getDias() {
        return dias;
    }

    public void setDias(EnumDias dias) {
        this.dias = dias;
    }
}
