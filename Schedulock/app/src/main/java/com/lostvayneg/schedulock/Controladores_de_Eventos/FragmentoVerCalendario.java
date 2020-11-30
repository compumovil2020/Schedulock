package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaActividades;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.Entidades.Localizacion;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Autenticacion_Firebase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class FragmentoVerCalendario extends Fragment {

    public static final String RUTA_ACTIVIDADES ="actividades/";

    private AdaptadorListaActividades adapterActividad;
    private RecyclerView recyclerViewActividades;
    private ImageView imgAdd;
    private ArrayList<Actividad> listaActividades;
    private CalendarView calendario;
    private View pantalla;
    private String idCalendario;
    private Calendario calendarioUsuario;
    private TextView nombre,etiqueta;
    public FirebaseDatabase baseDatos;
    private FirebaseUser usuario;
    public FirebaseStorage storageBD;
    public DatabaseReference referenciaBD;
    private Autenticacion_Firebase autenticacionFB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_calendario, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        recyclerViewActividades = pantalla.findViewById(R.id.list_actividades);
        imgAdd = pantalla.findViewById(R.id.btn_add_act);
        listaActividades = new ArrayList<>();
        calendario = pantalla.findViewById(R.id.calendario_ver_calendario);
        baseDatos = FirebaseDatabase.getInstance();
        storageBD = FirebaseStorage.getInstance();
        autenticacionFB = new Autenticacion_Firebase();
        usuario = autenticacionFB.getUsuario();
        calendarioUsuario= (Calendario) getArguments().getSerializable("calendar");
        nombre=pantalla.findViewById(R.id.tv_nombre);
        etiqueta=pantalla.findViewById(R.id.tv_etiqueta);
        nombre.setText(calendarioUsuario.getNombre());
        etiqueta.setText(calendarioUsuario.getEtiqueta());


        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("calendar",(Serializable)calendarioUsuario);
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_calendario_a_agregar_actividad,bundle);
            }
        });
        //cargar lista
        obtenerActividadesUsuario();
        //Mostrar datos


        return pantalla;
    }



    public void obtenerActividadesUsuario(){
        referenciaBD=baseDatos.getReference(RUTA_ACTIVIDADES);
        referenciaBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actividadBD: dataSnapshot.getChildren()) {
                    Actividad act = actividadBD.getValue(Actividad.class);
                    if(act.getIdUser().equals(usuario.getUid()) && act.getIdCalendario().equals(calendarioUsuario.getIdCalendario())){
                        listaActividades.add(act);
                    }

                }
                mostrarData();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void mostrarData(){
        recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterActividad = new AdaptadorListaActividades(getContext(), listaActividades);
        recyclerViewActividades.setAdapter(adapterActividad);

        adapterActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.ir_de_ver_calendario_a_ver_actividad);
            }
        });
    }

}