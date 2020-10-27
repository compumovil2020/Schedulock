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


import com.google.android.gms.maps.model.LatLng;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaNotas;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;
import java.util.Date;

public class FragmentoVerNotas extends Fragment {

    private AdaptadorListaNotas adapterNota;
    private RecyclerView recyclerViewNotas;
    private ImageView imgAdd;
    private ArrayList<Nota> listaNotas;
    private View pantalla;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_notas, container, false);

        recyclerViewNotas = pantalla.findViewById(R.id.list_notas);
        imgAdd = pantalla.findViewById(R.id.btn_add_nota);
        listaNotas = new ArrayList<>();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_notas_a_agregar_nota);
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
            //if (i%2 == 0)
                //listaNotas.add(new Nota("Nota "+i, new Actividad("Actividad "+i, "Categoria "+i, new Date(), new Date(),"prueba","alta","30 min antes","diaria",new LatLng(2.1867, -75.6233))));
            //else
                //listaNotas.add(new Nota("Nota "+i));
        }
    }

    public void mostrarData(){
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterNota = new AdaptadorListaNotas(getContext(), listaNotas);
        recyclerViewNotas.setAdapter(adapterNota);

        adapterNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ir_de_ver_notas_a_ver_nota);
            }
        });
    }


}