package com.lostvayneg.schedulock.Utilidades;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Autenticacion_Firebase {
    private FirebaseAuth autenticacionFB;

    public Autenticacion_Firebase(){
        autenticacionFB = FirebaseAuth.getInstance();
    }

    public FirebaseUser getUsuario(){
        return this.autenticacionFB.getCurrentUser();
    }
}
