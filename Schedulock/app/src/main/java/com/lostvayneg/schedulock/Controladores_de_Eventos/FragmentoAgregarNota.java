package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;

public class FragmentoAgregarNota extends Fragment {

    private View pantalla;
    private ImageView btnCancelar;
    private ImageView btnGuardarNota;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_agregar_nota, container, false);

        btnCancelar = pantalla.findViewById(R.id.btn_cancelar_agregar_nota);
        btnGuardarNota = pantalla.findViewById(R.id.btn_guardar_nota);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnGuardarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_agregar_nota_a_ver_nota);
            }
        });

        return pantalla;
    }

}