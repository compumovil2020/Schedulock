package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentoAgregarCalendario extends Fragment {

    private View pantalla;
    private Button btnGuardarCalendario;
    Acceso_Base_Datos acceso_base_datos;
    EditText nombreC,etiquetaC;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_agregar_calendario, container, false);

        btnGuardarCalendario = pantalla.findViewById(R.id.btn_guardar_calendario);
        nombreC=pantalla.findViewById(R.id.nombreCalendario);
        etiquetaC=pantalla.findViewById(R.id.etiqueta);
        acceso_base_datos=new Acceso_Base_Datos();
        btnGuardarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendario calendario=new Calendario();
                calendario.setNombre(nombreC.getText().toString());
                calendario.setEtiqueta(etiquetaC.getText().toString());
                acceso_base_datos.agregarNuevoCalendario(calendario);
                Bundle bundle=new Bundle();
                bundle.putSerializable("calendar", (Serializable) calendario);
                Navigation.findNavController(v).navigate(R.id.ir_de_agregar_calendario_a_ver_calendario,bundle);
            }
        });


        return pantalla;
    }

}