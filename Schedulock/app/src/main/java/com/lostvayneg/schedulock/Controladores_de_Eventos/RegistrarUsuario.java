package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.lostvayneg.schedulock.Entidades.Logro;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;
import com.lostvayneg.schedulock.Utilidades.Permisos;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText email_Registrarse;
    private EditText password_Registrarse;
    private EditText confirmar_Password;
    private FirebaseAuth autenticacionFB;
    private Button btnRegistrarse;
    private EditText nombre_Registrarse;
    private EditText edad_Registrarse;
    private RadioGroup genero_Registrarse;
    private Acceso_Base_Datos acceso_base_datos;
    private ImageView imagen_perfil;
    private static final int IMAGE_PICKER_REQUEST = 2;
    private Permisos permisos;
    private Uri uriFotoPerfil;
    private ProgressDialog progresoRegistro;
    private DatabaseReference referenciaBD;
    public FirebaseDatabase baseDatos;
    private FirebaseUser usuario;
    public static final String RUTA_LOGROS = "logros/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrar_usuario);
        email_Registrarse= findViewById(R.id.email_Registrarse);
        password_Registrarse=findViewById(R.id.password_registrarse);
        confirmar_Password=findViewById(R.id.confirmar_Password);
        this.autenticacionFB = FirebaseAuth.getInstance();
        acceso_base_datos = new Acceso_Base_Datos();
        btnRegistrarse=findViewById(R.id.btn_registrar_nuevo_usuario);
        nombre_Registrarse=findViewById(R.id.nombre_usuario_registrarse);
        edad_Registrarse = findViewById(R.id.edad_usuario_registrarse);
        genero_Registrarse = findViewById(R.id.genero_registrarse);
        genero_Registrarse.check(R.id.cbx_otro);
        imagen_perfil = findViewById(R.id.btn_seleccionar_imagen_registrar_usuario);
        uriFotoPerfil = null;
        permisos = new Permisos();
        progresoRegistro = new ProgressDialog(this);
        baseDatos= FirebaseDatabase.getInstance();
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarNuevoUsuario(view);
            }
        });

        imagen_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(permisos.solicitarPermisoSeleccionarImagen((Activity) v.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE,"Se necesita aceeso a la galería",IMAGE_PICKER_REQUEST)){
                    seleccionarImagen();
                }
                else{
                }
            }
        });

    }

    private void seleccionarImagen(){
        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage, IMAGE_PICKER_REQUEST);

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
                                guardarLogrosBD(user.getUid());
                                if(uriFotoPerfil != null){
                                    acceso_base_datos.guardarFotoPerfil(uriFotoPerfil, user.getUid());
                                    acceso_base_datos.referenciaSBD = acceso_base_datos.storageBD.getReference(acceso_base_datos.RUTA_IMAGENES).child(user.getUid());
                                    acceso_base_datos.referenciaSBD.putFile(uriFotoPerfil).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            acceso_base_datos.registrarNuevoUsuario(nuevoUsuario, user.getUid());
                                            progresoRegistro.dismiss();
                                            updateUI(user);
                                        }
                                    });
                                }
                                else{
                                    acceso_base_datos.registrarNuevoUsuario(nuevoUsuario, user.getUid());
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
    private void guardarLogrosBD(String id){
        ArrayList<Logro> listaLogros = new ArrayList<Logro>();
        Logro logro1= new Logro("Agenda de actividades actualizada","Se registraron al menos tres actividades",200,false);
        Logro logro2= new Logro("Si lo anoto, no se me olvida","Se registraron al menos tres notas",150,false);
        Logro logro3= new Logro("El más sociable","se ha invitado a otro usuario a una actividad",250,false);
        Logro logro4= new Logro("Mi primer Calendario","Se ha registrado un calendario",250,false);
        Logro logro5= new Logro("Persona de hábitos","Se han completado al menos tres hábitos",300,false);
        listaLogros.add(logro1);
        listaLogros.add(logro2);
        listaLogros.add(logro3);
        listaLogros.add(logro4);
        listaLogros.add(logro5);
        referenciaBD = baseDatos.getReference(RUTA_LOGROS + id+ "/");
        referenciaBD.setValue(listaLogros);

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
                return;
            }
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
        }
    }
    public void onClickIniciarSesion(View v){
        startActivity(new Intent(this, Login.class));
    }
}