package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;

public class FragmentoVerActividad extends Fragment {

    private View pantalla;
    private Button verUbicacion;
    private Button verMensajes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_actividad, container, false);

        verUbicacion = pantalla.findViewById(R.id.btn_ver_ubicacion_actividad);
        verMensajes = pantalla.findViewById(R.id.btn_ver_mensajes_actividad);

        verUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_actividad_a_ver_mapa);
            }
        });

        verMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_actividad_a_chat_actividad);
            }
        });
        return pantalla;
    }
}