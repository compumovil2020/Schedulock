package com.lostvayneg.schedulock.Enumerados;

public enum Enum_Niveles {
    NIVEL_1(1, 100),
    NIVEL_2(2, 200),
    NIVEL_3(3, 300),
    NIVEL_4(4, 400),
    NIVEL_5(5, 500),
    NIVEL_6(6, 600),
    NIVEL_7(7, 700),
    NIVEL_8(8, 800),
    NIVEL_9(9, 900),
    NIVEL_10(10, 1000);

    private int nivel;
    private int puntos_necesarios;

    Enum_Niveles(int nivel, int puntos_necesarios) {
        this.nivel = nivel;
        this.puntos_necesarios = puntos_necesarios;
    }

    public int getNivel(){
        return this.nivel;
    }

    public int getPuntosNecesarios(){
        return this.puntos_necesarios;
    }

    public Enum_Niveles obtenerNivel(int nivelUsuario){
        switch (nivelUsuario){
            case 1:
                return Enum_Niveles.NIVEL_1;
            case 2:
                return Enum_Niveles.NIVEL_2;
            case 3:
                return Enum_Niveles.NIVEL_3;
            case 4:
                return Enum_Niveles.NIVEL_4;
            case 5:
                return Enum_Niveles.NIVEL_5;
            case 6:
                return Enum_Niveles.NIVEL_6;
            case 7:
                return Enum_Niveles.NIVEL_7;
            case 8:
                return Enum_Niveles.NIVEL_8;
            case 9:
                return Enum_Niveles.NIVEL_9;
            case 10:
                return Enum_Niveles.NIVEL_10;
        }
        return null;
    }
}
