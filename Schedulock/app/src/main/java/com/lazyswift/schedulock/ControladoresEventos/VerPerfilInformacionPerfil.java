package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.lazyswift.schedulock.R;

public class VerPerfilInformacionPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil_informacion_perfil);
    }
    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }
}