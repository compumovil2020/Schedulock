package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;


public class FragmentoAgregarCalendario extends Fragment {

    private View pantalla;
    private Button btnGuardarCalendario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_agregar_calendario, container, false);

        btnGuardarCalendario = pantalla.findViewById(R.id.btn_guardar_calendario);

        btnGuardarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_agregar_calendario_a_ver_calendario);
            }
        });

        return pantalla;
    }

}