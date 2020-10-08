package com.lostvayneg.schedulock.Controladores_de_Eventos.Menu_Principal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lostvayneg.schedulock.R;

public class MenuPrincipal extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragmento_menu_principal, container, false);
    }

}