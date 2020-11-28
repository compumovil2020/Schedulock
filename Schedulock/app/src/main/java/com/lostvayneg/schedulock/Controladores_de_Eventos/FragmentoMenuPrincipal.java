package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lostvayneg.schedulock.Adaptadores.AdaptadorListaActividades;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.util.ArrayList;

public class FragmentoMenuPrincipal extends Fragment {

    private AdaptadorListaActividades adaptadorActividad;
    private View pantalla;
    private LinearLayout btnVerMapa;
    private Button btnVerCalendarios;
    private Button btnVerNotas;
    private Button btnVerLogros;
    private Acceso_Base_Datos baseDatos;
    private ArrayList<Actividad> actividadesParaHoy;
    private RecyclerView vistaActividadesParaHoy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_menu_principal, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        btnVerMapa = pantalla.findViewById(R.id.btn_ver_mapa_desde_menu);
        btnVerCalendarios = pantalla.findViewById(R.id.btn_ver_calendarios_desde_menu);
        btnVerNotas = pantalla.findViewById(R.id.btn_ver_notas_desde_menu);
        btnVerLogros = pantalla.findViewById(R.id.btn_ver_logros_desde_menu);
        vistaActividadesParaHoy = pantalla.findViewById(R.id.lista_actividades_para_hoy_menu_principal);
        //Pasar a ver el mapa de actividades
        btnVerMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.frg_chat_actividad);
            }
        });

        //Pasar a ver los calendarios
        btnVerCalendarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_calendarios);
            }
        });

        //Pasar a ver las notas
        btnVerNotas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_notas);
            }
        });

        btnVerLogros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_menu_principal_a_ver_logros);
            }
        });

        baseDatos = new Acceso_Base_Datos();
        return pantalla;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        baseDatos.obtenerActividadesUsuario();
        cargarActividades();
        mostrarListaActividadesParaHoy();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void cargarActividades(){
        actividadesParaHoy = baseDatos.obtenerActividadesUsuario();
    }

    private void mostrarListaActividadesParaHoy(){
        vistaActividadesParaHoy.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorActividad = new AdaptadorListaActividades(getContext(), actividadesParaHoy);
        vistaActividadesParaHoy.setAdapter(adaptadorActividad);

        adaptadorActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.frg_ver_actividad);
            }
        });
    }


}