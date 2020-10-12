package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaActividades;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

public class FragmentoVerCalendario extends Fragment {

    private AdaptadorListaActividades adapterActividad;
    private RecyclerView recyclerViewActividades;
    private ImageView imgAdd;
    private ArrayList<Actividad> listaActividades;
    private ImageView btnCalendario;
    private View pantalla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_calendario, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        recyclerViewActividades = pantalla.findViewById(R.id.list_actividades);
        imgAdd = pantalla.findViewById(R.id.btn_add_act);
        listaActividades = new ArrayList<>();
        btnCalendario = pantalla.findViewById(R.id.img_calendar);

        btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_calendario_a_ver_dia);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_calendario_a_agregar_actividad);
            }
        });
        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();

        return pantalla;
    }

    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaActividades.add(new Actividad("Actividad "+i, "Categoria "+i, "07/09/2020 10:00", "07/09/2020 12:00"));
        }
    }

    public void mostrarData(){
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividad = new AdaptadorListaActividades(getContext(), listaActividades);
        recyclerViewActividades.setAdapter(adapterActividad);

        adapterActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ir_de_ver_calendario_a_ver_actividad);
            }
        });
    }

}