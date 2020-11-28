package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.R;

public class FragmentoVerActividad extends Fragment {

    private View pantalla;
    private Button verUbicacion;
    private Button verMensajes;
    private TextView tituloActividad;
    private TextView descripcionActividad;
    private TextView prioridadActividad;
    private TextView fechaInicioActividad;
    private TextView fechaFinActividad;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_actividad, container, false);
        verUbicacion = pantalla.findViewById(R.id.btn_ver_ubicacion_actividad);
        verMensajes = pantalla.findViewById(R.id.btn_ver_mensajes_actividad);

        final Actividad actividadRecibida = (Actividad) getArguments().getSerializable("actividad");
        tituloActividad = pantalla.findViewById(R.id.titulo_ver_actividad);
        descripcionActividad = pantalla.findViewById(R.id.descripcion_ver_actividad);
        prioridadActividad = pantalla.findViewById(R.id.prioridad_ver_actividad);
        fechaInicioActividad = pantalla.findViewById(R.id.fecha_inicio_ver_actividad);
        fechaFinActividad = pantalla.findViewById(R.id.fecha_fin_ver_actividad);

        if (actividadRecibida != null) {
            tituloActividad.setText(actividadRecibida.getNombre());
            descripcionActividad.setText(actividadRecibida.getDescripcion());
            prioridadActividad.setText(actividadRecibida.getPrioridad());
            fechaInicioActividad.setText(actividadRecibida.toStringFechaInicio());
            fechaFinActividad.setText(actividadRecibida.toStringFechaFin());
        }


        verUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("actividad", actividadRecibida);
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_actividad_a_ver_mapa_unico, b);
            }
        });

        verMensajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("idActv", actividadRecibida.getIdActividad());
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_actividad_a_chat_actividad, b);
            }
        });
        return pantalla;
    }
}