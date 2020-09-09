package com.lazyswift.schedulock.ControladoresEventos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import com.lazyswift.schedulock.R;
import com.lazyswift.schedulock.VerActividad;

import java.util.Calendar;

public class RegistrarActividad extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button btnSeleccionarFechaInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad);
        btnSeleccionarFechaInicio = findViewById(R.id.btnSeleccionarFechaInicio);
        String[] arregloInvitadosEjemplo = {
                "invitado1@gmail.com",
                "invitado2@gmail.com",
                "invitado3@gmail.com",
                "invitado4@gmail.com",
                "invitado5@gmail.com",
                "invitado6@gmail.com"};
        ArrayAdapter<String> adaptadorInvitados = new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1,arregloInvitadosEjemplo);
        ListView listaUsuarios = findViewById(R.id.listaUsuarios);
        listaUsuarios.setAdapter(adaptadorInvitados);
    }
    public void goMenuDeslizable(View v){
        startActivity(new Intent(this, MenuDeslizableActivity.class));
    }
    public void mostrarDatePickerFechaInicio(View v){
        DatePickerDialog dpFechaInicio = new DatePickerDialog(
                this,
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

    public void onClickAgregarActividad(View v){
        startActivity(new Intent(this, VerActividad.class));
    }
}