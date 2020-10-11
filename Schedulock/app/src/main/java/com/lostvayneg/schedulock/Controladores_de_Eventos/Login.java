package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_iniciar_sesion);
    }

    public void onClickiniciarSesion(View v){
        Intent actividadMenuPrincipal = new Intent(this, ActividadPrincipal.class);
        startActivity(actividadMenuPrincipal);
    }

    public void onClickRegistrarse(View v){
        Intent actividadRegistroUsuario = new Intent(this, RegistrarUsuario.class);
        startActivity(actividadRegistroUsuario);
    }
}