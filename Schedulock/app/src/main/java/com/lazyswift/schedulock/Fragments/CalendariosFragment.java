package com.lazyswift.schedulock.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazyswift.schedulock.Adaptadores.AdapterCalendario;
import com.lazyswift.schedulock.Modelo.Calendario;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class CalendariosFragment extends Fragment {

    AdapterCalendario adapterCalendario;
    RecyclerView recyclerViewCalendarios;
    ArrayList<Calendario> listaCalendarios;

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    super.onCreate(savedInstanceState);
    //    setContentView(R.layout.activity_calendarios);
    //}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendarios, container, false);

        recyclerViewCalendarios = view.findViewById(R.id.list_calendarios);
        listaCalendarios = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();

        return view;
    }

    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaCalendarios.add(new Calendario("Calendario "+i, "Categoria "+i));
        }
    }

    public void mostrarData(){
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCalendario = new AdapterCalendario(getContext(), listaCalendarios);
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