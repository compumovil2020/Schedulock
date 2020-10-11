package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.lostvayneg.schedulock.R;

public class RegistrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrar_usuario);
    }

    public void onClickRegistrarse(View v){
        startActivity(new Intent(this, Login.class));
    }

    public void onClickIniciarSesion(View v){
        startActivity(new Intent(this, Login.class));
    }
}