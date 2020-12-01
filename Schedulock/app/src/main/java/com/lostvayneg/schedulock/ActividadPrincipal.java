package com.lostvayneg.schedulock;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
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
import com.lostvayneg.schedulock.Controladores_de_Eventos.Login;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.IOException;

public class ActividadPrincipal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    private ImageView img_perfil_usuario;
    private View headerMenuLateral;
    private Acceso_Base_Datos base_datos;
    private StorageReference referenciaSBD;
    public static final String RUTA_IMAGENES = "fotos_perfil/";
    private FirebaseStorage storageBD;
    private ProgressDialog cargaDatosUsuario;
    private File file_img_usuario;
    private Usuario usuario_conectado;
    public static final String PARAR_RECORDATORIOS = "parar_recordatorios";

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
        img_perfil_usuario = headerMenuLateral.findViewById(R.id.img_foto_perfil_usuario);
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
        base_datos = new Acceso_Base_Datos();
        storageBD = FirebaseStorage.getInstance();
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
                Bundle datosIniciales = new Bundle();
                datosIniciales.putSerializable("usuario", usuario_conectado);
                datosIniciales.putSerializable("file_imagen_usuario", file_img_usuario);
                controladorNavegacion.navigate(R.id.frg_ver_perfil, datosIniciales);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_menu_principal:
                controladorNavegacion.navigate(R.id.frg_menu_principal);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_calendarios:
                controladorNavegacion.navigate(R.id.frg_ver_calendarios);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_notas:
                controladorNavegacion.navigate(R.id.frg_ver_notas);
                menuLateral.closeDrawer(GravityCompat.START);
                return true;
            case R.id.frg_ver_logros:
                controladorNavegacion.navigate(R.id.frg_ver_logros);
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
        FirebaseUser currentUser = autenticacionFB.getCurrentUser();
        cargaDatosUsuario = new ProgressDialog(this);
        updateUI(currentUser);
    }

    private void updateUI(final FirebaseUser currentUser){
        if(currentUser!=null){
            referenciaBD = baseDatos.getReference(rutaUsuarios+currentUser.getUid());

            referenciaBD.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cargaDatosUsuario.setTitle("Iniciando Sesión");
                    cargaDatosUsuario.setMessage("Espere mientras cargamos sus datos");
                    cargaDatosUsuario.setCancelable(false);
                    cargaDatosUsuario.show();
                    Usuario usuarioConsultado = dataSnapshot.getValue(Usuario.class);
                    txt_nombre_usuario.setText(usuarioConsultado.getNombre());
                    usuario_conectado = usuarioConsultado;
                    txt_correo_usuario.setText(currentUser.getEmail());
                    final File localFile;
                    try {
                        localFile = File.createTempFile("images", "jpg");
                        referenciaSBD = storageBD.getReference(RUTA_IMAGENES).child(currentUser.getUid());
                        referenciaSBD.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        file_img_usuario = localFile;
                                        final Bitmap selectedImage = BitmapFactory.decodeFile(localFile.getPath());
                                        img_perfil_usuario.setImageBitmap(selectedImage);
                                        cargaDatosUsuario.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                cargaDatosUsuario.dismiss();
                            }
                        });
                    } catch (IOException e) {

                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
        }
    }


}