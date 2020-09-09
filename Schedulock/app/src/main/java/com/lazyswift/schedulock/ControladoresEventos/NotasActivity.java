package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lazyswift.schedulock.Adapters.AdapterNota;
import com.lazyswift.schedulock.Modelo.Actividad;
import com.lazyswift.schedulock.Modelo.Nota;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class NotasActivity extends AppCompatActivity {

    AdapterNota adapterNota;
    RecyclerView recyclerViewNotas;
    ImageView imgAdd;
    ArrayList<Nota> listaNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        recyclerViewNotas = findViewById(R.id.list_notas);
        imgAdd = findViewById(R.id.btn_add_calendar);
        listaNotas = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();

    }

    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            if (i%2 == 0)
                listaNotas.add(new Nota("Nota "+i, new Actividad("Actividad "+i, "categoria "+i, "07/09/2020 10:00", "07/09/2020 12:00")));
            else
                listaNotas.add(new Nota("Nota "+i));
        }
    }

    public void mostrarData(){
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapterNota = new AdapterNota(getBaseContext(), listaNotas);
        recyclerViewNotas.setAdapter(adapterNota);

        adapterNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), VerNotaActivity.class));
            }
        });
    }

    public void goAddNote(View v)
    {
        startActivity(new Intent(this, AgregarNotaActivity.class));
    }

    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }

}