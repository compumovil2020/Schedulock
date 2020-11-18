package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.UploadTask;
import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;
import com.lostvayneg.schedulock.Utilidades.Permisos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText email_Registrarse;
    private EditText password_Registrarse;
    private EditText confirmar_Password;
    private FirebaseAuth autenticacionFB;
    private Button btnRegistrarse;
    private EditText nombre_Registrarse;
    private EditText edad_Registrarse;
    private RadioGroup genero_Registrarse;
    private Acceso_Base_Datos baseDatos;
    private ImageView imagen_perfil;
    private static final int IMAGE_PICKER_REQUEST = 2;
    private static final int CAMERA_PICKER_REQUEST = 1;
    private Permisos permisos;
    private Uri uriFotoPerfil;
    private ProgressDialog progresoRegistro;
    private String ruta_imagen_camara;
    private File foto_camara;
    private static String RUTA_FOTOS = "Fotos Schedulock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrar_usuario);
        email_Registrarse= findViewById(R.id.email_Registrarse);
        password_Registrarse=findViewById(R.id.password_registrarse);
        confirmar_Password=findViewById(R.id.confirmar_Password);
        this.autenticacionFB = FirebaseAuth.getInstance();
        baseDatos = new Acceso_Base_Datos();
        btnRegistrarse=findViewById(R.id.btn_registrar_nuevo_usuario);
        nombre_Registrarse=findViewById(R.id.nombre_usuario_registrarse);
        edad_Registrarse = findViewById(R.id.edad_usuario_registrarse);
        genero_Registrarse = findViewById(R.id.genero_registrarse);
        genero_Registrarse.check(R.id.cbx_otro);
        imagen_perfil = findViewById(R.id.btn_seleccionar_imagen_registrar_usuario);
        uriFotoPerfil = null;
        permisos = new Permisos();
        progresoRegistro = new ProgressDialog(this);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarNuevoUsuario(view);
            }
        });

        imagen_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpcionesSelecionarImagen();
            }
        });

    }

    private void mostrarOpcionesSelecionarImagen(){
        final CharSequence[] opciones = {"Tomar Foto","Seleccionar de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccione una opcion");
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto")){
                    lanzarCamara();
                }else if (opciones[which].equals("Seleccionar de Galeria")){
                    seleccionarImagen();
                }
                else{
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void lanzarCamara(){
        if(permisos.solicitarPermisoSeleccionarImagen(this, Manifest.permission.CAMERA,"Se necesita aceeso a la galería",CAMERA_PICKER_REQUEST) &&
                permisos.solicitarPermisoSeleccionarImagen(this, Manifest.permission.READ_EXTERNAL_STORAGE,"Se necesita aceeso a la galería",CAMERA_PICKER_REQUEST)){
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            File miFile = new File(Environment.getExternalStorageDirectory(), RUTA_FOTOS);
            boolean isCreada = miFile.exists();
            if(!isCreada){
                isCreada = miFile.mkdirs();
            }
            if(isCreada){
                Long consecutivo = System.currentTimeMillis()/1000;
                String nombreFoto = consecutivo.toString() + ".jpg";
                ruta_imagen_camara = Environment.getExternalStorageDirectory() + File.separator + RUTA_FOTOS + File.separator + nombreFoto;
                foto_camara = new File(ruta_imagen_camara);
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto_camara));
                startActivityForResult(takePictureIntent, CAMERA_PICKER_REQUEST);
            }
        }
        else{
        }
    }

    private void seleccionarImagen(){
        if(permisos.solicitarPermisoSeleccionarImagen(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,"Se necesita aceeso a la galería",IMAGE_PICKER_REQUEST)){
            Intent pickImage = new Intent(Intent.ACTION_PICK);
            pickImage.setType("image/*");
            startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);
        }
        else{
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void registrarNuevoUsuario(View v){
        final String nombreIn = nombre_Registrarse.getText().toString();
        final String emailIn= email_Registrarse.getText().toString();
        final String edad = edad_Registrarse.getText().toString();
        String passwordIn=password_Registrarse.getText().toString();
        String confPasswordIn=confirmar_Password.getText().toString();
        if(validarCampos(nombreIn, emailIn, edad, passwordIn,confPasswordIn)){
            autenticacionFB.createUserWithEmailAndPassword(emailIn, passwordIn)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                progresoRegistro.setTitle("Creando usuario");
                                progresoRegistro.setMessage("Espere por favor mientras se registra");
                                progresoRegistro.setCancelable(false);
                                progresoRegistro.show();
                                final FirebaseUser user = autenticacionFB.getCurrentUser();

                                final Usuario nuevoUsuario = new Usuario();
                                nuevoUsuario.setNombre(nombreIn);
                                nuevoUsuario.setEmail(emailIn);
                                nuevoUsuario.setEdad(edad);
                                int idGenero = genero_Registrarse.getCheckedRadioButtonId();
                                String generoIn = "";
                                if(idGenero == R.id.cbx_femenino)
                                    generoIn = "Femenino";
                                else if(idGenero == R.id.cbx_masculino)
                                    generoIn = "Masculino";
                                else
                                    generoIn = "Otro";
                                nuevoUsuario.setGenero(generoIn);
                                if(uriFotoPerfil != null){
                                    baseDatos.guardarFotoPerfil(uriFotoPerfil, user.getUid());
                                    baseDatos.referenciaSBD = baseDatos.storageBD.getReference(baseDatos.RUTA_IMAGENES).child(user.getUid());
                                    baseDatos.referenciaSBD.putFile(uriFotoPerfil).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            baseDatos.registrarNuevoUsuario(nuevoUsuario, user.getUid());
                                            progresoRegistro.dismiss();
                                            updateUI(user);
                                        }
                                    });
                                }
                                else{
                                    baseDatos.registrarNuevoUsuario(nuevoUsuario, user.getUid());
                                    progresoRegistro.dismiss();
                                    updateUI(user);
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegistrarUsuario.this, "El correo ingresado ya esta registrado",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }

    private boolean validarCampos(String nombreIn, String emailIn, String edad, String passwordIn, String confPasswordIn){
        if(!nombreIn.isEmpty()){
            if(!emailIn.isEmpty()){
                if (isEmailValid(emailIn)){
                    if(!edad.isEmpty()){
                        if(!passwordIn.isEmpty()) {
                            if(!confPasswordIn.isEmpty()){
                                if(passwordIn.equals(confPasswordIn) ){
                                    return true;
                                } else{
                                    Toast.makeText(RegistrarUsuario.this, "Las contraseñas son incorrectas",
                                            Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(RegistrarUsuario.this, "Escriba la confirmación de contraseña",
                                        Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(RegistrarUsuario.this, "Escriba su contraseña",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(RegistrarUsuario.this, "Escriba su edad",
                                Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(RegistrarUsuario.this, "El correo esta mal formado",
                            Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(RegistrarUsuario.this, "Escriba un correo",
                        Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(RegistrarUsuario.this, "Escriba un nombre de usuario",
                    Toast.LENGTH_LONG).show();
        }
        return  false;
    }

    private void updateUI(FirebaseUser user ){
        if(user!= null){
            Intent actividadPrincipal= new Intent(this, ActividadPrincipal.class);
            startActivity(actividadPrincipal);
        }
        else{

        }

    }

    private boolean isEmailValid(String email) {
        if (!email.contains("@") ||
                !email.contains(".") ||
                email.length() < 5)
            return false;
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case IMAGE_PICKER_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    seleccionarImagen();
                } else {
                }
            }
            break;
            case CAMERA_PICKER_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    lanzarCamara();
                } else {
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IMAGE_PICKER_REQUEST:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        uriFotoPerfil = imageUri;
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imagen_perfil.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }break;
            case CAMERA_PICKER_REQUEST:
                if(resultCode == RESULT_OK){
                    MediaScannerConnection.scanFile(getBaseContext(), new String[]{ruta_imagen_camara}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("Path: ", path);
                                    uriFotoPerfil = uri;
                                }
                            });
                    Bitmap imageBitmap = BitmapFactory.decodeFile(ruta_imagen_camara);
                    imagen_perfil.setImageBitmap(imageBitmap);
                }
                break;
        }
    }
    public void onClickIniciarSesion(View v){
        startActivity(new Intent(this, Login.class));
    }
}