package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.squareup.picasso.Picasso;


public class FragmentoVerNota extends Fragment {

    private View pantalla;
    private ImageView btnEditarNota;
    private ImageView btnEliminarNota;
    private TextView tituloNota;
    private TextView tituloActividad;
    private ImageView adjunto;
    private TextView desc;

    private Nota nota;

    public static final String RUTA_ACTIVIDADES ="actividades/";
    public static final String RUTA_NOTAS ="notas/";
    private FirebaseDatabase fireDB;
    private StorageReference mStorageRef;
    private FirebaseAuth authF;
    private FirebaseUser user;

    public static final String RUTA_USUARIOS ="usuarios/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fireDB = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        authF = FirebaseAuth.getInstance();
        user = authF.getCurrentUser();

        nota = (Nota) getArguments().getSerializable("nota");

        pantalla = inflater.inflate(R.layout.fragmento_ver_nota, container, false);

        btnEditarNota = pantalla.findViewById(R.id.btn_editar_nota);
        btnEliminarNota = pantalla.findViewById(R.id.btn_eliminar_nota);
        tituloNota = pantalla.findViewById(R.id.tv_ver_nombre_nota);
        tituloActividad = pantalla.findViewById(R.id.tv_ver_actividad_nota);
        desc = pantalla.findViewById(R.id.tv_ver_desc_nota);
        adjunto = pantalla.findViewById(R.id.iv_ver_adj_nota);

        tituloNota.setText(nota.getNombre());
        desc.setText(nota.getDescripcion());

        mStorageRef.child("adjuntos_notas/" + nota.getId()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).
                                load(uri).into(adjunto);
                    }
                });

        if (nota.getIdActividad() != null && !nota.getIdActividad().equals(""))
            consultarActividad(nota.getIdActividad());
        else
            tituloActividad.setText("");

        btnEditarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("nota", nota);
                Navigation.findNavController(v).navigate(R.id.ir_de_ver_nota_a_editar_nota,b);
            }
        });

        btnEliminarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = fireDB.getReference(RUTA_NOTAS + nota.getId());
                ref.removeValue();
                StorageReference adjuntoRef = mStorageRef.child("adjuntos_notas/" + nota.getId());
                adjuntoRef.delete();
                final DatabaseReference refUser = fireDB.getReference(RUTA_USUARIOS + user.getUid());
                refUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Usuario usr = datasnapshot.getValue(Usuario.class);
                        usr.getIdsNotas().remove(nota.getId());
                        refUser.setValue(usr);

                        if (nota.getIdActividad() != null) {
                            final DatabaseReference refAct = fireDB.getReference(RUTA_ACTIVIDADES + nota.getIdActividad());
                            refAct.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                                    Actividad act = datasnapshot.getValue(Actividad.class);
                                    act.setIdNota(null);
                                    refAct.setValue(act);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        Navigation.findNavController(getView()).navigate(R.id.ir_de_ver_nota_a_ver_notas);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                //Toast.makeText(getContext(), adjuntoRef.getPath(), Toast.LENGTH_SHORT).show();
            }
        });

        return pantalla;
    }

    public void consultarActividad (String id) {
        DatabaseReference ref = fireDB.getReference(RUTA_ACTIVIDADES + id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Actividad act = dataSnapshot.getValue(Actividad.class);
                String activ = act.getNombre();
                tituloActividad.setText(activ);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }


}