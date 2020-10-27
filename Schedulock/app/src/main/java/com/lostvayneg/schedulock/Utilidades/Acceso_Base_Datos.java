package com.lostvayneg.schedulock.Utilidades;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lostvayneg.schedulock.Controladores_de_Eventos.Login;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Usuario;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;

public class Acceso_Base_Datos {

    public FirebaseDatabase baseDatos;
    public FirebaseStorage storageBD;
    public DatabaseReference referenciaBD;
    private Autenticacion_Firebase autenticacionFB;
    public static final String RUTA_USUARIOS ="usuarios/";
    public static final String RUTA_ACTIVIDADES ="actividades/";
    public static final String RUTA_IMAGENES = "fotos_perfil/";
    private ArrayList<Actividad> listaActividades;
    private FirebaseUser usuario;
    public StorageReference referenciaSBD;

    public Acceso_Base_Datos(){
        baseDatos = FirebaseDatabase.getInstance();
        storageBD = FirebaseStorage.getInstance();
        autenticacionFB = new Autenticacion_Firebase();
        this.listaActividades = new ArrayList<>();
        usuario = autenticacionFB.getUsuario();
    }

    public boolean registrarNuevoUsuario(Usuario nuevoUsuario, String idUsuario ){

        try {
            referenciaBD = baseDatos.getReference(RUTA_USUARIOS + idUsuario);
            referenciaBD.setValue(nuevoUsuario);
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean agregarNuevaActividad(Actividad nuevaActividad){
        try{
            int cantidadActividadesUsuario = this.listaActividades.size();
            Log.i("Cant", cantidadActividadesUsuario +"");
            String idActividad = String.valueOf(cantidadActividadesUsuario + 1);
            referenciaBD = baseDatos.getReference(RUTA_ACTIVIDADES + usuario.getUid()+ "/" + idActividad + "/");
            referenciaBD.setValue(nuevaActividad);
        }
        catch (Exception e){
            return false;
        }
        return true;
    }

    public ArrayList<Actividad> obtenerActividadesUsuario(){
        referenciaBD = baseDatos.getReference(RUTA_ACTIVIDADES + usuario.getUid() + "/");
        referenciaBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actividadBD: dataSnapshot.getChildren()
                     ) {
                    Actividad act = actividadBD.getValue(Actividad.class);
                    listaActividades.add(act);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return  listaActividades;
    }

    public boolean guardarFotoPerfil(Uri uriFotoPerfil, String userID) {

        final boolean[] estadoTarea = {false};

        referenciaSBD = storageBD.getReference(RUTA_IMAGENES).child(userID);
        referenciaSBD.putFile(uriFotoPerfil).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                estadoTarea[0] = true;
            }
        });
        return estadoTarea[0];
    }

    public File obtenerFotoPerfil() throws IOException {
        File localFile = File.createTempFile("images", "jpg");
        referenciaSBD = storageBD.getReference(RUTA_IMAGENES).child(usuario.getUid());
        referenciaSBD.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });
        return localFile;
    }
}