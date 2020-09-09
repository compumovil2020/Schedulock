package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.R;

public class MostrarMarker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_marker);
    }

    public void goMostrarRuta(View v){
        startActivity(new Intent(this, MostrarRuta.class));
    }
}