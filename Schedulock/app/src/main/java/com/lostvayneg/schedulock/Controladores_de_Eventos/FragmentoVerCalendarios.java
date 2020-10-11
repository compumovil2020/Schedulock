package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaCalendarios;
import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

public class FragmentoVerCalendarios extends Fragment {

    private AdaptadorListaCalendarios adapterCalendario;
    private RecyclerView recyclerViewCalendarios;
    private ImageView imgAdd;
    private ArrayList<Calendario> listaCalendarios;
    private View pantalla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_calendarios, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        recyclerViewCalendarios = pantalla.findViewById(R.id.list_calendarios);
        imgAdd = pantalla.findViewById(R.id.btn_add_calendar);
        listaCalendarios = new ArrayList<>();

        //cargar lista
        cargarLista();
        //Mostrar datos
        mostrarData();

        return pantalla;
    }


    public void cargarLista(){
        for(int i = 1; i < 6; i++)
        {
            listaCalendarios.add(new Calendario("Calendario "+i, "Etiqueta "+i));
        }
    }

    public void mostrarData(){
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCalendario = new AdaptadorListaCalendarios(getContext(), listaCalendarios);
        recyclerViewCalendarios.setAdapter(adapterCalendario);

        adapterCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ir_de_ver_calendarios_a_ver_calendario);
            }
        });
    }

    public void goAddCalendar(View v)
    {
        //startActivity(new Intent(this, RegistrarCalendario.class));
    }
}