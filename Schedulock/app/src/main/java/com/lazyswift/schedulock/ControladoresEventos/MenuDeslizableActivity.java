package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.R;
import com.lazyswift.schedulock.VerLogros;

public class MenuDeslizableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deslizable);
    }

    public void goPerfil(View v){
        startActivity(new Intent(this, VerPerfil.class));
    }

    public void goMenuPrincipal(View v){
        startActivity(new Intent(this, MenuPrincipalActivity.class));
    }

    public void goCalendarios(View v){
        startActivity(new Intent(this, CalendariosActivity.class));
    }

    public void goNotas(View v){

        startActivity(new Intent(this, NotasActivity.class));
    }

    public void goLogros(View v){
        startActivity(new Intent(this, VerLogros.class));
    }

    public void goVerPerfil(View v){
        Intent actividadVerPerfil = new Intent(this, VerPerfil.class);
        startActivity(actividadVerPerfil);
    }

    public void goBack(View v){
        finish();
    }
}