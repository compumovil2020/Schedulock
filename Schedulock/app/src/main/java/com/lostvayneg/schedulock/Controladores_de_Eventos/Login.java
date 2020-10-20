package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lostvayneg.schedulock.ActividadPrincipal;
import com.lostvayneg.schedulock.R;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private  EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_iniciar_sesion);
        mAuth = FirebaseAuth.getInstance();
        email= findViewById(R.id.email_user_iniciar_sesion);
        password= findViewById(R.id.password_user_iniciar_sesion);
    }

    public void onClickiniciarSesion(View v){
        String emailin;
        String passwordin;
        emailin=email.getText().toString();
        passwordin=password.getText().toString();
        if(!emailin.isEmpty() && isEmailValid(emailin)){
            if(!passwordin.isEmpty()){
                mAuth.signInWithEmailAndPassword(emailin, passwordin)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser){
        if(currentUser!=null){
            Intent intent = new Intent(getBaseContext(), ActividadPrincipal.class);
            intent.putExtra("user", currentUser.getEmail());
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