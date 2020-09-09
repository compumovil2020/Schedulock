package com.lazyswift.schedulock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuDeslizableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_deslizable);
    }

    public void goPerfil(View v){
        //startActivity(new Intent(this, Perfil.class));
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
        //startActivity(new Intent(this, Logros.class));
    }
}