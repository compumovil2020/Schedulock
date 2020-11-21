package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.Enumerados.Enum_Niveles;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.io.File;
import java.io.IOException;


public class FragmentoVerPerfil extends Fragment {

    private View pantalla;
    private ImageView imagen_usuario;
    private TextView nombre_usuario;
    private TextView correo_usuario;
    private TextView nivel_usuario;
    private TextView puntos_usuario;
    private TextView puntos_restantes_nivel;
    private ProgressBar progeso_exp;
    private TextView cantidad_logros;
    private TextView cantidad_calendarios;
    private TextView cantidad_notas;
    private TextView cantidad_actividades;
    private EditText editar_nombre_usuario;
    private TextView editar_correo_usuario;
    private EditText editar_edad_usuario;
    private TextView genero_usuario;
    private Button guardar_cambios;

    private Usuario usuario_actual;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        pantalla = inflater.inflate(R.layout.fragmento_ver_perfil, container, false);

        imagen_usuario = pantalla.findViewById(R.id.img_foto_perfil_usuario_ver_perfil);
        nombre_usuario = pantalla.findViewById(R.id.nombre_usuario_ver_perfil);
        correo_usuario = pantalla.findViewById(R.id.correo_usuario_ver_perfil);
        nivel_usuario = pantalla.findViewById(R.id.nivel_usuario_ver_perfil);
        puntos_usuario = pantalla.findViewById(R.id.cantidad_exp_ver_perfil);
        puntos_restantes_nivel = pantalla.findViewById(R.id.puntos_restantes_usuario_ver_perfil);
        progeso_exp = pantalla.findViewById(R.id.progreso_exp_ver_perfil);
        cantidad_logros = pantalla.findViewById(R.id.logros_usuario_ver_perfil);
        cantidad_calendarios = pantalla.findViewById(R.id.calendarios_usuario_ver_perfil);
        cantidad_notas = pantalla.findViewById(R.id.notas_usuario_ver_perfil);
        cantidad_actividades = pantalla.findViewById(R.id.actividades_usuario_ver_perfil);
        editar_nombre_usuario = pantalla.findViewById(R.id.entrada_nombre_usuario_ver_perfil);
        editar_correo_usuario = pantalla.findViewById(R.id.correo2_usuario_ver_perfil);
        editar_edad_usuario = pantalla.findViewById(R.id.entrada_edad_usuario_ver_perfil);
        genero_usuario = pantalla.findViewById(R.id.genero_usuario_ver_perfil);
        guardar_cambios = pantalla.findViewById(R.id.btn_guardar_cambios_ver_perfil);

        cargarDatosIniciales();

        return pantalla;
    }

    private void cargarDatosIniciales(){
        Bundle datosIniciales = getArguments();
        if(datosIniciales != null){
            usuario_actual = (Usuario) datosIniciales.getSerializable("usuario");
            if(usuario_actual != null){
                nombre_usuario.setText(usuario_actual.getNombre());
                correo_usuario.setText(usuario_actual.getEmail());
                nivel_usuario.setText("Nivel " + usuario_actual.getNivel());
                puntos_usuario.setText("" + usuario_actual.getExperiencia());
                puntos_restantes_nivel.setText(calcularPuntosRestantes());
                editar_nombre_usuario.setText(usuario_actual.getNombre());
                editar_correo_usuario.setText(usuario_actual.getEmail());
                editar_edad_usuario.setText(usuario_actual.getEdad());
                genero_usuario.setText(usuario_actual.getGenero());
            }

            File img_usuario = (File) datosIniciales.getSerializable("file_imagen_usuario");
            if(img_usuario != null){
                Bitmap bmImagen = BitmapFactory.decodeFile(img_usuario.getPath());
                imagen_usuario.setImageBitmap(bmImagen);
            }
        }
    }

    private String calcularPuntosRestantes(){
        Enum_Niveles enum_niveles = null;

        String expRestante;
        if(usuario_actual.getNivel() == 0){
            enum_niveles = Enum_Niveles.NIVEL_1;
            expRestante = usuario_actual.getExperiencia() + "/" + enum_niveles.getPuntosNecesarios() + " Puntos";
        }
        else{
            enum_niveles = enum_niveles.obtenerNivel(usuario_actual.getNivel());
            expRestante = usuario_actual.getExperiencia() + "/" + enum_niveles.getPuntosNecesarios() + " Puntos";
        }
        return expRestante;
    }
}