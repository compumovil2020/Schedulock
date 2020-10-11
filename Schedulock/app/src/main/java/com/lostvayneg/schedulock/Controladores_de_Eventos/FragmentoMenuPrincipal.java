package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.R;

public class FragmentoMenuPrincipal extends Fragment {

    private View pantalla;
    private LinearLayout btnVerMapa;
    private Button btnVerCalendarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_menu_principal, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        btnVerMapa = pantalla.findViewById(R.id.btn_ver_mapa_desde_menu);
        btnVerCalendarios = pantalla.findViewById(R.id.btn_ver_calendarios_desde_menu);
        //Pasar a ver el mapa de actividades
        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_mapa);
            }
        });

        //Pasar a ver los calendarios
        btnVerCalendarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_calendarios);
            }
        });

        return pantalla;
    }

    public void onClickVerCalendarios(View v){
        Toast.makeText(v.getContext(), "Ver Calendario", Toast.LENGTH_LONG).show();
        //Intent actividadCalendarios = new Intent(this,CalendariosActivity.class);
        //startActivity(actividadCalendarios);
    }

    public void onClickVerNotas(View v){
        Toast.makeText(v.getContext(), "Ver Calendario", Toast.LENGTH_LONG).show();
        //Intent actividadNotas = new Intent(this, NotasActivity.class);
        //startActivity(actividadNotas);
    }

    public void onClickVerLogros(View v){
        Toast.makeText(v.getContext(), "Ver Calendario", Toast.LENGTH_LONG).show();
        //Intent actividadLogros = new Intent(this, VerLogros.class);
        //startActivity(actividadLogros);
    }

    public void onClickVerActividad(View v){
        Toast.makeText(v.getContext(), "Ver Calendario", Toast.LENGTH_LONG).show();
        //Intent actividad = new Intent(this, VerActividad.class);
        //startActivity(actividad);
    }

}