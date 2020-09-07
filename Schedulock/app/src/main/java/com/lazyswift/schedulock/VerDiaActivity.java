package com.lazyswift.schedulock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lazyswift.schedulock.Adapters.AdapterActividad;
import com.lazyswift.schedulock.Modelo.Actividad;

import java.util.ArrayList;

public class VerDiaActivity extends AppCompatActivity {

    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    ImageView imgAdd;
    ArrayList<Actividad> listaActividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_dia);

        recyclerViewActividades = findViewById(R.id.list_actividades);
        imgAdd = findViewById(R.id.btn_add_act);
        listaActividades = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();

    }


    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaActividades.add(new Actividad("Actividad "+i, "Categoria "+i, "10:00", "12:00"));
        }
    }

    public void mostrarData(){
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapterActividad = new AdapterActividad(getBaseContext(), listaActividades);
        recyclerViewActividades.setAdapter(adapterActividad);

        adapterActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}