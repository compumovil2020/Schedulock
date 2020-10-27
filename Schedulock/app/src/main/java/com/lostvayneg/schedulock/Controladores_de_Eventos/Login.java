package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

public class Login extends AppCompatActivity {
    private FirebaseAuth autenticacionFB;
    private EditText email;
    private EditText password;
    private ImageButton btn_iniciar_con_google;
    private GoogleSignInClient clienteGoogle;
    private int RC_SIGN_IN = 1;

    private Acceso_Base_Datos baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_iniciar_sesion);
        autenticacionFB = FirebaseAuth.getInstance();
        baseDatos= new Acceso_Base_Datos();
        email= findViewById(R.id.email_user_iniciar_sesion);
        password= findViewById(R.id.password_user_iniciar_sesion);
        btn_iniciar_con_google=findViewById(R.id.btn_iniciar_con_google);
        btn_iniciar_con_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoogleSignInOptions google=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                clienteGoogle = GoogleSignIn.getClient(view.getContext(), google);
                iniciarSesionGoogle();

            }
        });
    }

    private void iniciarSesionGoogle(){
        Intent intentSesion = clienteGoogle.getSignInIntent();
        startActivityForResult(intentSesion, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> tareaIniciarSesion = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSingInResult(tareaIniciarSesion);
        }
    }

    private void handleSingInResult(Task<GoogleSignInAccount> tareaCompletada){
        try {
            GoogleSignInAccount usuario = tareaCompletada.getResult(ApiException.class);
            Toast.makeText(Login.this, "Se pudo", Toast.LENGTH_LONG).show();
            IniciarSesionFirebaseConGoogle(usuario);
        } catch (ApiException e) {
            Toast.makeText(Login.this, "No se pudo", Toast.LENGTH_LONG).show();
            IniciarSesionFirebaseConGoogle(null);
        }
    }

    private void IniciarSesionFirebaseConGoogle(final GoogleSignInAccount usuario){
        AuthCredential credencialGoogle = GoogleAuthProvider.getCredential(usuario.getIdToken(), null);
        autenticacionFB.signInWithCredential(credencialGoogle).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioFB = autenticacionFB.getCurrentUser();
                    Usuario nuevoUsuario = new Usuario();
                    nuevoUsuario.setNombre(usuario.getDisplayName());
                    nuevoUsuario.setEmail(usuario.getEmail());
                    nuevoUsuario.setEdad("Sin llenar");
                    nuevoUsuario.setGenero("Sin llenar");
                    baseDatos.registrarNuevoUsuario(nuevoUsuario, usuarioFB.getUid());
                    updateUI(usuarioFB);
                }
                else{
                    updateUI(null);
                }
            }
        });
    }
    public void onClickiniciarSesion(View v){
        String emailin;
        String passwordin;
        emailin=email.getText().toString();
        passwordin=password.getText().toString();
        if(!emailin.isEmpty() && isEmailValid(emailin)){
            if(!passwordin.isEmpty()){
                autenticacionFB.signInWithEmailAndPassword(emailin, passwordin)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = autenticacionFB.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Login.this, "Usuario o contraseña inválidos.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
            else{
                Toast.makeText(Login.this,"Ingrese una contraseña",Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(Login.this,"Ingrese un correo",Toast.LENGTH_LONG).show();
        }
    }

    public void onClickRegistrarse(View v){
        Intent actividadRegistroUsuario = new Intent(this, RegistrarUsuario.class);
        startActivity(actividadRegistroUsuario);
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = autenticacionFB.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(), ActividadPrincipal.class);
            startActivity(intent);
        } else {
            email.setText("");
            password.setText("");
        }
    }
    private boolean isEmailValid(String email) {
        if (!email.contains("@") ||
                !email.contains(".") ||
                email.length() < 5)
            return false;
        return true;
    }

}