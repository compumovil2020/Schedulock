package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaCalendarios;
import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;
import com.lostvayneg.schedulock.Utilidades.Autenticacion_Firebase;

import java.util.ArrayList;

public class FragmentoVerCalendarios extends Fragment {

    private AdaptadorListaCalendarios adapterCalendario;
    private RecyclerView recyclerViewCalendarios;
    private ImageView imgAdd;
    private ArrayList<Calendario> listaCalendarios;
    private ImageView btnAgregarCalendario;
    private View pantalla;
    private Acceso_Base_Datos acceso_base_datos;
    public FirebaseDatabase baseDatos;
    public FirebaseStorage storageBD;
    public DatabaseReference referenciaBD;
    private Autenticacion_Firebase autenticacionFB;
    public static final String RUTA_CALENDARIOS="calendarios/";
    private FirebaseUser usuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_ver_calendarios, container, false);
        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        recyclerViewCalendarios = pantalla.findViewById(R.id.list_calendarios);
        imgAdd = pantalla.findViewById(R.id.btn_add_calendar);
        baseDatos = FirebaseDatabase.getInstance();
        storageBD = FirebaseStorage.getInstance();
        autenticacionFB = new Autenticacion_Firebase();
        usuario = autenticacionFB.getUsuario();
        listaCalendarios = new ArrayList<>();
        obtenerCalendariosUsuario();
        System.out.println("TAMAÑo"+listaCalendarios.size());
        //cargar lista
        //Mostrar datos


        btnAgregarCalendario = pantalla.findViewById(R.id.btn_add_calendar);

        btnAgregarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_calendarios_a_agregar_calendario);
            }
        });

        return pantalla;
    }


    public void obtenerCalendariosUsuario(){
        referenciaBD=baseDatos.getReference(RUTA_CALENDARIOS);
        referenciaBD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actividadBD: dataSnapshot.getChildren()) {
                    Calendario cal = actividadBD.getValue(Calendario.class);
                    if(cal.getUserId().equals(usuario.getUid())){
                        listaCalendarios.add(cal);
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
        recyclerViewCalendarios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterCalendario = new AdaptadorListaCalendarios(getContext(), listaCalendarios);
        recyclerViewCalendarios.setAdapter(adapterCalendario);

    }

}