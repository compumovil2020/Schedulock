package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lazyswift.schedulock.Adapters.AdapterCalendario;
import com.lazyswift.schedulock.Modelo.Calendario;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class CalendariosActivity extends AppCompatActivity {

    AdapterCalendario adapterCalendario;
    RecyclerView recyclerViewCalendarios;
    ImageView imgAdd;
    ArrayList<Calendario> listaCalendarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarios);

        recyclerViewCalendarios = findViewById(R.id.list_calendarios);
        imgAdd = findViewById(R.id.btn_add_calendar);
        listaCalendarios = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();



    }

    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaCalendarios.add(new Calendario("Calendario "+i, "Etiqueta "+i));
        }
    }

    public void mostrarData(){
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        adapterCalendario = new AdapterCalendario(getBaseContext(), listaCalendarios);
        recyclerViewCalendarios.setAdapter(adapterCalendario);

        adapterCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getBaseContext(), VerCalendarioActivity.class));
            }
        });
    }

    public void goAddCalendar(View v)
    {
        startActivity(new Intent(this, RegistrarCalendario.class));
    }

    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }

}