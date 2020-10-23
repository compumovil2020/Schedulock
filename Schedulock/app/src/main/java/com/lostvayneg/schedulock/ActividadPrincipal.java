package com.lostvayneg.schedulock;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.lostvayneg.schedulock.Controladores_de_Eventos.Login;

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
    private FirebaseAuth mAuth;
    private NavigationView vistaNavegacion;
    private NavController controladorNavegacion;
    private DrawerLayout menuLateral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_principal);
        //Inflacion de elementos
        mAuth = FirebaseAuth.getInstance();
        //Barra superior de la app
        Toolbar barraNavegacion = findViewById(R.id.barra_navegacion);
        setSupportActionBar(barraNavegacion);
        //Menu lateral
        menuLateral = findViewById(R.id.menu_lateral);
        //Panel del lateral
        vistaNavegacion = findViewById(R.id.vista_navegacion);
        vistaNavegacion.setNavigationItemSelectedListener(this);
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
                mAuth.signOut();
                Intent actividadLogin = new Intent(this, Login.class);
                startActivity(actividadLogin);
                return true;
        }
        return true;
    }
}