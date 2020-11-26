package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.UploadTask;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class FragmentoEditarNota extends Fragment {

    private Nota nota;

    private View pantalla;
    private ImageView btnGuardarCambios;
    private ImageView btnCancelarCambios;
    private ImageView btnSubir;
    private ImageView btnTomar;

    private FirebaseDatabase fireDB;
    private FirebaseAuth authF;
    private FirebaseUser user;
    private StorageReference mStorageRef;

    private static final String RUTA_NOTAS = "notas/";
    public static final String RUTA_ACTIVIDADES ="actividades/";

    private EditText tituloNota;
    private TextView tituloActv;
    private ImageView adjunto;
    private EditText desc;

    private static final int OPEN_CAMERA = 0;
    private static final int OPEN_GALLERY = 1;
    private static final int IMAGE_PICKER_REQUEST = 2;
    private static final int REQUEST_IMAGE_CAPTURE = 3;

    private Uri imagenFire;
    private ByteArrayInputStream bitmapFire;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fireDB = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        authF = FirebaseAuth.getInstance();
        user = authF.getCurrentUser();

        nota = (Nota) getArguments().getSerializable("nota");

        pantalla = inflater.inflate(R.layout.fragmento_editar_nota, container, false);

        btnGuardarCambios = pantalla.findViewById(R.id.btn_guardar_cambios_nota);
        btnCancelarCambios = pantalla.findViewById(R.id.btn_cancelar_cambios_nota);
        btnSubir = pantalla.findViewById(R.id.iv_subirFoto);
        btnTomar = pantalla.findViewById(R.id.iv_tomar_foto);

        tituloNota = pantalla.findViewById(R.id.et_nombre_nota);
        desc = pantalla.findViewById(R.id.et_desc_nota);
        adjunto = pantalla.findViewById(R.id.iv_adjunto);
        tituloActv = pantalla.findViewById(R.id.tv_nombre_act);

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
            tituloActv.setText("");

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarNota();

            }
        });

        btnCancelarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putSerializable("nota", nota);
                Navigation.findNavController(v).navigate(R.id.ir_de_editar_nota_a_ver_nota, b);
            }
        });

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarPermiso(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                        "Necesito acceder a la galeria", OPEN_GALLERY);
                abrirGaleria();
            }
        });

        btnTomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarPermiso(getActivity(), Manifest.permission.CAMERA,
                        "Necesito acceder a la camara", OPEN_CAMERA);
                abrirCamara();
            }
        });

        return pantalla;
    }

    private void solicitarPermiso(Activity context, String permiso, String justificacion, int idPermiso)
    {
        if(ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)){
                Toast.makeText(getContext(), justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idPermiso);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case OPEN_GALLERY: {
                Toast.makeText(getContext(), "GALERIA", Toast.LENGTH_SHORT);
                abrirGaleria();
                break;
            }
            case OPEN_CAMERA: {
                Toast.makeText(getContext(), "CAMARA", Toast.LENGTH_SHORT);
                abrirCamara();
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case IMAGE_PICKER_REQUEST: {
                if (resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        this.imagenFire = imageUri;
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        adjunto.setImageBitmap(selectedImage);
                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
            }
            case REQUEST_IMAGE_CAPTURE: {
                if(resultCode == RESULT_OK) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    adjunto.setImageBitmap(imageBitmap);

                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                    bitmapFire = new ByteArrayInputStream(bos.toByteArray());
                }
                break;
            }
        }

    }

    private void abrirGaleria() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
        } else {
            Toast.makeText(getContext(), "Para esta funcionalidad es necesario que de permisos al almacenamiento", Toast.LENGTH_SHORT);
        }
    }

    private void abrirCamara() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } else {
                Log.println(Log.ERROR, "CAMARA", "ERROR");
            }
        } else {
            Toast.makeText(getContext(), "Para esta funcionalidad es necesario que de permisos a la camara", Toast.LENGTH_SHORT);
        }
    }

    private void subirAdjunto(final Nota n) {
        Uri file = this.imagenFire;
        StorageReference adjuntoRef = mStorageRef.child("adjuntos_notas/" + n.getId());

        if (file == null){
            adjuntoRef.putStream(bitmapFire);
        } else {

            adjuntoRef.putFile(file)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Bundle b = new Bundle();
                            b.putSerializable("nota", n);
                            Navigation.findNavController(getView()).navigate(R.id.ir_de_editar_nota_a_ver_nota, b);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Log.e("ERROR", exception.getMessage());
                        }
                    });
        }
    }

    private void actualizarNota() {

        DatabaseReference refNota = fireDB.getReference(RUTA_NOTAS + nota.getId());

        nota.setNombre(tituloNota.getText().toString());
        nota.setDescripcion(desc.getText().toString());

        refNota.setValue(nota);

        if (imagenFire != null && bitmapFire != null) {
            subirAdjunto(nota);
        } else {
            Bundle b = new Bundle();
            b.putSerializable("nota", nota);
            Navigation.findNavController(getView()).navigate(R.id.ir_de_editar_nota_a_ver_nota, b);
        }
    }

    public void consultarActividad (String id) {
        DatabaseReference ref = fireDB.getReference(RUTA_ACTIVIDADES + id);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Actividad act = dataSnapshot.getValue(Actividad.class);
                String activ = act.getNombre();
                tituloActv.setText(activ);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
    }


}