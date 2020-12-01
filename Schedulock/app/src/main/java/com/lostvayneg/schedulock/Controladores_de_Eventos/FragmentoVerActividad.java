package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.R;

public class FragmentoVerActividad extends Fragment {

    private View pantalla;
    private Button verUbicacion;
    private Button verMensajes;
    private Button verNota;
    private TextView tituloActividad;
    private TextView descripcionActividad;
    private TextView prioridadActividad;
    private TextView fechaInicioActividad;
    private TextView fechaFinActividad;


    public static final String RUTA_NOTAS ="notas/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_actividad, container, false);
        verUbicacion = pantalla.findViewById(R.id.btn_ver_ubicacion_actividad);
        verMensajes = pantalla.findViewById(R.id.btn_ver_mensajes_actividad);
        verNota = pantalla.findViewById(R.id.btn_ver_nota);

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

        verNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (actividadRecibida.getIdNota() != null) {
                    if(actividadRecibida.getIdNota() != "") {
                        DatabaseReference refaux = FirebaseDatabase.getInstance().getReference(RUTA_NOTAS + actividadRecibida.getIdNota());
                        refaux.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Nota n = dataSnapshot.getValue(Nota.class);
                                Bundle b = new Bundle();
                                b.putSerializable("nota", n);
                                Navigation.findNavController(view).navigate(R.id.ir_de_ver_actividad_a_ver_nota, b);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else {
                        Bundle b = new Bundle();
                        b.putString("actividad", actividadRecibida.getIdActividad());
                        Navigation.findNavController(view).navigate(R.id.ir_de_ver_actividad_a_agregar_nota, b);
                    }
                } else {
                    Bundle b = new Bundle();
                    b.putString("actividad", actividadRecibida.getIdActividad());
                    Navigation.findNavController(view).navigate(R.id.ir_de_ver_actividad_a_agregar_nota, b);
                }
            }
        });

        return pantalla;
    }
}