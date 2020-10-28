package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaActividades;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Localizacion;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;
import java.util.Date;

public class FragmentoVerCalendario extends Fragment {

    private AdaptadorListaActividades adapterActividad;
    private RecyclerView recyclerViewActividades;
    private ImageView imgAdd;
    private ArrayList<Actividad> listaActividades;
    private CalendarView calendario;
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
        calendario = pantalla.findViewById(R.id.calendario_ver_calendario);

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
            Actividad act1 = new Actividad();
            act1.setNombre("Nombre vacio");
            act1.setFrencuenciaR("Una vez");
            act1.setPrioridad("Alta");
            act1.setDescripcion("Ejemplo");
            act1.setRecordatorio("30 Minutos Antes");
            act1.setCategoria("1");
            act1.setInicio(new Date());
            act1.setFin(new Date());
            act1.setLocalizacion(new Localizacion(2.1867, -75.6233));
            listaActividades.add(act1);
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