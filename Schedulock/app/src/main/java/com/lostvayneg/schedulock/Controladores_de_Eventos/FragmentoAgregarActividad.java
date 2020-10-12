package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lostvayneg.schedulock.R;

import java.util.Calendar;

public class FragmentoAgregarActividad extends Fragment implements DatePickerDialog.OnDateSetListener {

    private Button btnSeleccionarFechaInicio;
    private Button btnSeleccionarFechaFin;
    private Button btnGuardarActividad;
    private View pantalla;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_agregar_actividad, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        btnSeleccionarFechaInicio = pantalla.findViewById(R.id.btn_seleccionar_fecha_inicio);
        btnSeleccionarFechaFin = pantalla.findViewById(R.id.btn_seleccionar_fecha_fin);
        btnGuardarActividad = pantalla.findViewById(R.id.btn_guardar_actividad);
        //Seleccionar fecha inicio
        btnSeleccionarFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFecha(v);
            }
        });

        //Seleccionar Fecha fin
        btnSeleccionarFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFecha(v);
            }
        });

        btnGuardarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.ir_de_agregar_actividad_a_ver_actividad);
            }
        });

        String[] arregloInvitadosEjemplo = {
                "invitado1@gmail.com",
                "invitado2@gmail.com",
                "invitado3@gmail.com",
                "invitado4@gmail.com",
                "invitado5@gmail.com",
                "invitado6@gmail.com"};
        ArrayAdapter<String> adaptadorInvitados = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_list_item_1,arregloInvitadosEjemplo);
        ListView listaUsuarios = pantalla.findViewById(R.id.listaUsuarios);
        listaUsuarios.setAdapter(adaptadorInvitados);
        return pantalla;
    }

    public void seleccionarFecha(View v){
        DatePickerDialog dpFechaInicio = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dpFechaInicio.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String fechaSelecionada = year+"/"+month+"/"+dayOfMonth;
        this.btnSeleccionarFechaInicio.setText(fechaSelecionada);
    }

}