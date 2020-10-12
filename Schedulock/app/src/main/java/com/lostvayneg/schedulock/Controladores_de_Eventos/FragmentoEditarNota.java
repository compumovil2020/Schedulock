package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;


public class FragmentoEditarNota extends Fragment {

    private View pantalla;
    private ImageView btnGuardarCambios;
    private ImageView btnCancelarCambios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_editar_nota, container, false);

        btnGuardarCambios = pantalla.findViewById(R.id.btn_guardar_cambios_nota);
        btnCancelarCambios = pantalla.findViewById(R.id.btn_cancelar_cambios_nota);

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_editar_nota_a_ver_nota);
            }
        });

        btnCancelarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_editar_nota_a_ver_nota);
            }
        });

        return pantalla;
    }


}