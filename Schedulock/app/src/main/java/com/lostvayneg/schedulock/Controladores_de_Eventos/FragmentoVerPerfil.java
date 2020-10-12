package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lostvayneg.schedulock.R;


public class FragmentoVerPerfil extends Fragment {

    private View pantalla;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_perfil, container, false);

        return pantalla;
    }

}