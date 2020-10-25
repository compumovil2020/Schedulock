package com.lostvayneg.schedulock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Controladores_de_Eventos.Login;
import com.lostvayneg.schedulock.Entidades.Usuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActividadPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private AppBarConfiguration listaFragmentos;
    private NavigationView vistaNavegacion;
    private NavController controladorNavegacion;
    private DrawerLayout menuLateral;
    private FirebaseAuth autenticacionFB;
    private FirebaseDatabase baseDatos;
    private DatabaseReference referenciaBD;
    public static final String rutaUsuarios="usuarios/";
    private TextView txt_nombre_usuario;
    private TextView txt_correo_usuario;
    private View headerMenuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        //Inflacion de elementos
        autenticacionFB = FirebaseAuth.getInstance();
        baseDatos= FirebaseDatabase.getInstance();
        //Barra superior de la app
        Toolbar barraNavegacion = findViewById(R.id.barra_navegacion);
        setSupportActionBar(barraNavegacion);
        //Menu lateral
        menuLateral = findViewById(R.id.menu_lateral);
        //Panel del lateral
        vistaNavegacion = findViewById(R.id.vista_navegacion);
        vistaNavegacion.setNavigationItemSelectedListener(this);
        headerMenuLateral = vistaNavegacion.getHeaderView(0);
        txt_nombre_usuario = headerMenuLateral.findViewById(R.id.txt_nombre_usuario);
        txt_correo_usuario = headerMenuLateral.findViewById(R.id.txt_email_usuario);
        listaFragmentos = new AppBarConfiguration.Builder(
                R.id.frg_ver_perfil,
                R.id.frg_menu_principal,
                R.id.frg_ver_calendarios,
                R.id.frg_ver_notas,
                R.id.frg_ver_logros)
                .setDrawerLayout(menuLateral)
                .build();
        controladorNavegacion = Navigation.findNavController(this, R.id.vista_fragmentos);
        NavigationUI.setupActionBarWithNavController(this, controladorNavegacion, listaFragmentos);
        FirebaseUser currentUser = autenticacionFB.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController controladorNavegacion = Navigation.findNavController(this, R.id.vista_fragmentos);
        return NavigationUI.navigateUp(controladorNavegacion, listaFragmentos)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.frg_ver_perfil:
                controladorNavegacion.navigate(R.id.ir_de_menu_principal_a_ver_perfil);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_menu_principal:
                controladorNavegacion.navigate(R.id.ir_a_menu_principal);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_calendarios:
                controladorNavegacion.navigate(R.id.ir_de_menu_principal_a_ver_calendarios);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_notas:
                controladorNavegacion.navigate(R.id.ir_de_menu_principal_a_ver_notas);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_logros:
                controladorNavegacion.navigate(R.id.ir_de_menu_principal_a_ver_logros);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_cerrar_sesion:
                autenticacionFB.signOut();
                Intent actividadLogin = new Intent(this, Login.class);
                startActivity(actividadLogin);
                return true;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void updateUI(final FirebaseUser currentUser){
        if(currentUser!=null){
            referenciaBD = baseDatos.getReference(rutaUsuarios+currentUser.getUid());

            referenciaBD.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Usuario usuarioConsultado = dataSnapshot.getValue(Usuario.class);
                    txt_nombre_usuario.setText(usuarioConsultado.getNombre());
                    txt_correo_usuario.setText(currentUser.getEmail());

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
        }
    }
}