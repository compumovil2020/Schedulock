package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lazyswift.schedulock.Adapters.AdapterActividad;
import com.lazyswift.schedulock.Modelo.Actividad;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class VerCalendarioActivity extends AppCompatActivity {

    AdapterActividad adapterActividad;
    RecyclerView recyclerViewActividades;
    ImageView imgAdd;
    ArrayList<Actividad> listaActividades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_calendario);

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
            listaActividades.add(new Actividad("Actividad "+i, "Categoria "+i, "07/09/2020 10:00", "07/09/2020 12:00"));
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

    public void goVerDia(View v)
    {
        startActivity(new Intent(this, VerDiaActivity.class));
    }

    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }

    public void onClickAgregarActividad(View v){
        startActivity(new Intent(this, RegistrarActividad.class));
    }

}