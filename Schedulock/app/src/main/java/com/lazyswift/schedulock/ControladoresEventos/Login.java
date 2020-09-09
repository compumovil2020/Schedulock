package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.lazyswift.schedulock.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
    }

    public void onClickiniciarSesion(View v){
        Intent actividadMenuPrincipal = new Intent(this, MenuPrincipalActivity.class);
        startActivity(actividadMenuPrincipal);
    }

    public void onClickRegistrarse(View v){
        Intent actividadRegistroUsuario = new Intent(this, RegistrarUsuario.class);
        startActivity(actividadRegistroUsuario);
    }
}