package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Adaptadores.MensajeAdapter;
import com.lostvayneg.schedulock.Entidades.Mensaje;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;


public class FragmentoChatActividad extends Fragment {

    private static final String PATH_MENSAJES = "mensajes/";
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private static  final String idActividad = "-MNEfVb6_XvKTwyA7t_R";
    private ArrayList<Mensaje> mensajes;
    private int pos = 0;
    public MensajeAdapter mensajeAdapter;
    public ValueEventListener listener;
    public DatabaseReference refMensaje;
    public RecyclerView miLista;
    public ImageView enviar;
    public EditText mensajeEnviar;
    private View pantalla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla
        pantalla = inflater.inflate(R.layout.fragmento_chat_actividad, container, false);
        miLista = pantalla.findViewById(R.id.listaMensajes);
        mensajeEnviar = pantalla.findViewById(R.id.mensajeEnviar);
        enviar = pantalla.findViewById(R.id.imageEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarMensaje();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Toast.makeText(getContext(), "Primero", Toast.LENGTH_SHORT).show();
        obtenerMensajes();
        return pantalla;
    }

    private void enviarMensaje() {
        DatabaseReference refDB, aux;
        refDB = database.getReference(PATH_MENSAJES);
        String id= refDB.push().getKey();
        aux = database.getReference(PATH_MENSAJES+id);
        Mensaje mensaje = new Mensaje();
        mensaje.setIdMensaje(id);
        mensaje.setIdUser(mAuth.getUid());
        mensaje.setIdActividad(idActividad);
        mensaje.setMensaje(mensajeEnviar.getText().toString());
        DatabaseReference refDBUsers = database.getReference("usuarios/"+mAuth.getUid());

        refDBUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                mensaje.setNombreUsr(user.getNombre());
                mensaje.setPos(pos);
                aux.setValue(mensaje);
                mensajeEnviar.getText().clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void obtenerMensajes() {
        mensajes = new ArrayList<>();
        final FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            refMensaje = database.getReference(PATH_MENSAJES);
            refMensaje.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mensajes.clear();
                    for (DataSnapshot mensajeBD: dataSnapshot.getChildren()) {
                        Mensaje msj = mensajeBD.getValue(Mensaje.class);
                        Toast.makeText(getContext(), "Antes if", Toast.LENGTH_SHORT).show();
                        if (idActividad.equals(msj.getIdActividad())) {
                            Toast.makeText(getContext(), msj.getMensaje(), Toast.LENGTH_SHORT).show();
                            mensajes.add(msj);
                        }
                    }
                    pos = mensajes.size();
                    mostrarData();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void mostrarData() {
        miLista.setLayoutManager(new LinearLayoutManager(getContext()));
        mensajeAdapter = new MensajeAdapter(getContext(), mensajes);
        miLista.setAdapter(mensajeAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        refMensaje.removeEventListener(listener);
    }
}