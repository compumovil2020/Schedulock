package com.lazyswift.schedulock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.ControladoresEventos.ChatActivity;
import com.lazyswift.schedulock.ControladoresEventos.Mapa;
import com.lazyswift.schedulock.ControladoresEventos.MenuDeslizableActivity;

public class VerActividad extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_actividad);
    }

    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }

    public void onClickUbicacion(View v){
        startActivity(new Intent(this, Mapa.class));
    }

    public void onClickMensajes(View v){
        startActivity(new Intent(this, ChatActivity.class));
    }
}