package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lostvayneg.schedulock.R;

public class FragmentoMenuPrincipal extends Fragment {

    private View pantalla;
    private LinearLayout btnVerMapa;
    private Button btnVerCalendarios;
    private Button btnVerNotas;
    private Button btnVerLogros;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_menu_principal, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        btnVerMapa = pantalla.findViewById(R.id.btn_ver_mapa_desde_menu);
        btnVerCalendarios = pantalla.findViewById(R.id.btn_ver_calendarios_desde_menu);
        btnVerNotas = pantalla.findViewById(R.id.btn_ver_notas_desde_menu);
        btnVerLogros = pantalla.findViewById(R.id.btn_ver_logros_desde_menu);
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

        //Pasar a ver las notas
        btnVerNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_notas);
            }
        });

        btnVerLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_logros);
            }
        });

        return pantalla;
    }


    public void onClickVerActividad(View v){
        Toast.makeText(v.getContext(), "Ver Calendario", Toast.LENGTH_LONG).show();
        //Intent actividad = new Intent(this, VerActividad.class);
        //startActivity(actividad);
    }

}