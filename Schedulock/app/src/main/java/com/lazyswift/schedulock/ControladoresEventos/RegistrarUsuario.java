package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lazyswift.schedulock.R;

public class RegistrarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
    }

    public void onClickRegistrarse(View v){
        startActivity(new Intent(this, Login.class));
    }

    public void onClickIniciarSesion(View v){
        startActivity(new Intent(this, Login.class));
    }
}