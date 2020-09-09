package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.R;
import com.lazyswift.schedulock.VerActividad;
import com.lazyswift.schedulock.VerLogros;

public class MenuPrincipalActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }

    public void onClickVerCalendarios(View v){
        Intent actividadCalendarios = new Intent(this,CalendariosActivity.class);
        startActivity(actividadCalendarios);
    }

    public void onClickVerNotas(View v){
        Intent actividadNotas = new Intent(this, NotasActivity.class);
        startActivity(actividadNotas);
    }

    public void onClickVerLogros(View v){
        Intent actividadLogros = new Intent(this, VerLogros.class);
        startActivity(actividadLogros);
    }

    public void onClickVerMapa(View v){
        Intent actividadMapa = new Intent(this, Mapa.class);
        startActivity(actividadMapa);
    }

    public void onClickVerActividad(View v){
        Intent actividad = new Intent(this, VerActividad.class);
        startActivity(actividad);
    }


}