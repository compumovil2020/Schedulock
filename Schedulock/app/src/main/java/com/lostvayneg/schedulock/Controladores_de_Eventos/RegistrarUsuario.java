package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;

public class RegistrarUsuario extends AppCompatActivity {
    private EditText email_Registrarse;
    private EditText password_Registrarse;
    private EditText confirmar_Password;
    private FirebaseAuth autenticacionFB;
    private Button btnRegistrarse;
    private FirebaseDatabase baseDatos;
    private DatabaseReference referenciaBD;
    public static final String rutaUsuarios="usuarios/";
    private EditText nombre_Registrarse;
    private EditText edad_Registrarse;
    private RadioGroup genero_Registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_registrar_usuario);
        email_Registrarse= findViewById(R.id.email_Registrarse);
        password_Registrarse=findViewById(R.id.password_registrarse);
        confirmar_Password=findViewById(R.id.confirmar_Password);
        this.autenticacionFB = FirebaseAuth.getInstance();
        baseDatos= FirebaseDatabase.getInstance();
        btnRegistrarse=findViewById(R.id.btn_registrar_nuevo_usuario);
        nombre_Registrarse=findViewById(R.id.nombre_usuario_registrarse);
        edad_Registrarse = findViewById(R.id.edad_usuario_registrarse);
        genero_Registrarse = findViewById(R.id.genero_registrarse);
        genero_Registrarse.check(R.id.cbx_otro);
        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarNuevoUsuario(view);
            }
        });

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
                                FirebaseUser user = autenticacionFB.getCurrentUser();

                                Usuario nuevoUsuario = new Usuario();
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
                                referenciaBD = baseDatos.getReference(rutaUsuarios + user.getUid());
                                referenciaBD.setValue(nuevoUsuario);
                                updateUI(user);
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

    public void onClickIniciarSesion(View v){
        startActivity(new Intent(this, Login.class));
    }
}