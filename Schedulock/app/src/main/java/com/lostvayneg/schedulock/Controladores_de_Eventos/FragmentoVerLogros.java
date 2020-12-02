package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaActividades;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaLogros;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Logro;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;
import com.lostvayneg.schedulock.Utilidades.Autenticacion_Firebase;

import java.util.ArrayList;


public class FragmentoVerLogros extends Fragment {

    private View pantalla;
    private Acceso_Base_Datos acceso_base_datos;
    private AdaptadorListaLogros adaptadorLogros;
    private ArrayList<Logro> listaLogros;
    private RecyclerView vistaLogros;
    public FirebaseDatabase baseDatos;
    public FirebaseStorage storageBD;
    public DatabaseReference referenciaBD;
    private Autenticacion_Firebase autenticacionFB;
    public static final String RUTA_USUARIOS ="usuarios/";
    public static final String RUTA_ACTIVIDADES ="actividades/";
    public static final String RUTA_IMAGENES = "fotos_perfil/";
    public static final String RUTA_LOGROS = "logros/";
    private ArrayList<Actividad> listaActividades;
    private FirebaseUser usuario;
    public StorageReference referenciaSBD;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_logros, container, false);
        vistaLogros=pantalla.findViewById(R.id.lista_logros_ver_logros);
        acceso_base_datos = new Acceso_Base_Datos();
        baseDatos= FirebaseDatabase.getInstance();
        listaLogros= new ArrayList<Logro>();
        usuario= FirebaseAuth.getInstance().getCurrentUser();

        return pantalla;


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        obtenerLogrosUsuario();
        mostrarListaLogros();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public ArrayList<Logro> obtenerLogrosUsuario(){
        referenciaBD = baseDatos.getReference(RUTA_LOGROS + usuario.getUid() + "/");
        referenciaBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot logroBD: dataSnapshot.getChildren()
                ) {
                    Logro logro= logroBD.getValue(Logro.class);
                    listaLogros.add(logro);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  listaLogros;
    }
    private void mostrarListaLogros(){
        vistaLogros.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorLogros = new AdaptadorListaLogros(getContext(), listaLogros);
        vistaLogros.setAdapter(adaptadorLogros);

        adaptadorLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.frg_ver_logros);
            }
        });
    }
}
