package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaNotas;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.util.ArrayList;
import java.util.Date;

public class FragmentoVerNotas extends Fragment {

    private AdaptadorListaNotas adapterNota;
    private RecyclerView recyclerViewNotas;
    private ImageView imgAdd;
    private ArrayList<Nota> listaNotas;
    private View pantalla;

    private FirebaseDatabase fireDB;
    private FirebaseAuth authF;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_notas, container, false);

        recyclerViewNotas = pantalla.findViewById(R.id.list_notas);
        imgAdd = pantalla.findViewById(R.id.btn_add_nota);
        listaNotas = new ArrayList<>();

        fireDB = FirebaseDatabase.getInstance();
        authF = FirebaseAuth.getInstance();
        user = authF.getCurrentUser();

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_notas_a_agregar_nota);
            }
        });

        //cargar lista
        cargarLista();

        return pantalla;
    }

    public void cargarLista(){
        listaNotas = new ArrayList<>();
        DatabaseReference ref = fireDB.getReference(Acceso_Base_Datos.RUTA_NOTAS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot notaDB: dataSnapshot.getChildren()) {
                    Nota nota = notaDB.getValue(Nota.class);
                    if (nota.getIdUsuario().equals(user.getUid())) {
                        listaNotas.add(nota);
                    }
                }
                mostrarData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void mostrarData(){
        recyclerViewNotas.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterNota = new AdaptadorListaNotas(getContext(), listaNotas);
        recyclerViewNotas.setAdapter(adapterNota);


        adapterNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerViewNotas.getChildLayoutPosition(view);
                Bundle b = new Bundle();
                b.putSerializable("nota", listaNotas.get(position));
                Navigation.findNavController(view).navigate(R.id.ir_de_ver_notas_a_ver_nota, b);
            }
        });
    }


}