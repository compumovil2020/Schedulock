package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;


public class FragmentoVerNota extends Fragment {

    private View pantalla;
    private ImageView btnEditarNota;
    private ImageView btnEliminarNota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_nota, container, false);

        btnEditarNota = pantalla.findViewById(R.id.btn_editar_nota);
        btnEliminarNota = pantalla.findViewById(R.id.btn_eliminar_nota);

        btnEditarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_nota_a_editar_nota);
            }
        });

        btnEliminarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return pantalla;
    }


}