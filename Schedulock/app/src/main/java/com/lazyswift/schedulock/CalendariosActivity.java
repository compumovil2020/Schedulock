package com.lazyswift.schedulock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.Adapters.AdapterCalendario;
import com.lazyswift.schedulock.Modelo.Calendario;

import java.util.ArrayList;

public class CalendariosActivity extends AppCompatActivity {

    AdapterCalendario adapterCalendario;
    RecyclerView recyclerViewCalendarios;
    ArrayList<Calendario> listaCalendarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarios);

        recyclerViewCalendarios = findViewById(R.id.list_calendarios);
        listaCalendarios = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();


    }

    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaCalendarios.add(new Calendario("Calendario "+i, "Categoria "+i));
        }
    }

    public void mostrarData(){
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapterCalendario = new AdapterCalendario(getBaseContext(), listaCalendarios);
        recyclerViewCalendarios.setAdapter(adapterCalendario);

        adapterCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaCalendarios.get(recyclerViewCalendarios.getChildAdapterPosition(view)).nombre;
                String categoria = listaCalendarios.get(recyclerViewCalendarios.getChildAdapterPosition(view)).categoria;
            }
        });
    }

}