package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.model.LatLng;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Localizacion;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.io.IOException;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentoAgregarActividad extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button btnSeleccionarFechaInicio;
    private Button btnSeleccionarFechaFin;
    private Button btnGuardarActividad;
    private View pantalla;
    private Spinner importancia,recordatorio,frecuencia;
    private Actividad actividad;
    private Double latitud=null;
    private Double longitud=null;
    private int contfecha;
    private Date fechaI,fechaf, fechaSelecionada;
    private EditText nombre,categoria,descripcion,ubicacion;
    private Geocoder mGeocoder;

    private Acceso_Base_Datos baseDatos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Se infla el elemento por el cual se van a obtener los elementos de la pantalla.
        pantalla = inflater.inflate(R.layout.fragmento_agregar_actividad, container, false);

        //Para obtener elementos de la pantalla se usa el view pantalla antes del metodo find view by id
        btnSeleccionarFechaInicio = pantalla.findViewById(R.id.btn_seleccionar_fecha_inicio);
        btnSeleccionarFechaFin = pantalla.findViewById(R.id.btn_seleccionar_fecha_fin);
        btnGuardarActividad = pantalla.findViewById(R.id.btn_guardar_actividad);
        nombre=pantalla.findViewById(R.id.editNombre);
        categoria=pantalla.findViewById(R.id.editCategoria);
        descripcion=pantalla.findViewById(R.id.editDescripcion);
        importancia=pantalla.findViewById(R.id.sprSelecionarNivelImportancia);
        recordatorio=pantalla.findViewById(R.id.sprSelecionarRecordatorio);
        frecuencia=pantalla.findViewById(R.id.sprSelecionarFrecuenciaRecordatorio);
        ubicacion=pantalla.findViewById(R.id.editDireccion);
        mGeocoder = new Geocoder(pantalla.getContext());
        baseDatos = new Acceso_Base_Datos();

        fechaSelecionada = new Date();

        btnSeleccionarFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contfecha=1;
                seleccionarFecha(v);
            }
        });

        //Seleccionar Fecha fin
        btnSeleccionarFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contfecha=2;
                seleccionarFecha(v);
            }
        });

        btnGuardarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarActividad();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.baseDatos.obtenerActividadesUsuario();
    }

    private void agregarActividad(){
        if(verificarCampos()){
            actividad=new Actividad();

            actividad.setNombre(nombre.getText().toString());
            actividad.setCategoria(categoria.getText().toString());
            actividad.setInicio(fechaI);
            actividad.setFin(fechaf);
            actividad.setDescripcion(descripcion.getText().toString());
            actividad.setRecordatorio(recordatorio.getSelectedItem().toString());
            actividad.setPrioridad(importancia.getSelectedItem().toString());
            actividad.setFrencuenciaR(frecuencia.getSelectedItem().toString());
            actividad.setLocalizacion(new Localizacion(latitud, longitud));
            baseDatos.agregarNuevaActividad(actividad);
            Navigation.findNavController(getView()).navigate(R.id.ir_de_agregar_actividad_a_ver_actividad);
        }
    }

    private boolean verificarCampos(){

        if(!nombre.getText().toString().isEmpty()){
            if(!categoria.getText().toString().isEmpty()){
                if(!descripcion.getText().toString().isEmpty()){
                    if(fechaI != null){
                        if(fechaf != null){
                            if(!ubicacion.getText().toString().isEmpty()){
                                encontrarUbicacion(ubicacion.getText().toString());
                            }
                            return true;
                        }
                        else{
                            Toast.makeText(pantalla.getContext(), "Seleccione la fecha y hora de fin", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(pantalla.getContext(), "Seleccione la fecha y hora de inicio", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(pantalla.getContext(), "Escriba la descripcion de la actividad", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(pantalla.getContext(), "Escriba la categoria de la actividad", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(pantalla.getContext(), "Escriba el nombre de la actividad", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void encontrarUbicacion(String ubicacion) {
        String addressString = ubicacion;
        if (!addressString.isEmpty()) {
            try {
                List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                if (addresses != null && !addresses.isEmpty()) {
                    Address addressResult = addresses.get(0);
                    latitud=addressResult.getLatitude();
                    longitud=addressResult.getLongitude();
                } else {
                    Toast.makeText(pantalla.getContext(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {Toast.makeText(pantalla.getContext(), "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
    }

    public void seleccionarFecha(View v){
        DatePickerDialog dpFecha = new DatePickerDialog(
                getContext(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        dpFecha.show();

        TimePickerDialog tdHora = new TimePickerDialog(
                getContext(),
                (TimePickerDialog.OnTimeSetListener) this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                false
        );
        tdHora.show();

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        fechaSelecionada.setYear(year);
        fechaSelecionada.setMonth(month);
        fechaSelecionada.setDate(dayOfMonth);

        String textoBotones =
                fechaSelecionada.getYear()+ "/" +
                        fechaSelecionada.getMonth()+ "/" +
                        fechaSelecionada.getDate()+ " " +
                        fechaSelecionada.getHours()+ ":"+
                        fechaSelecionada.getMinutes();
        if (contfecha==1){
            fechaI=fechaSelecionada;
            this.btnSeleccionarFechaInicio.setText(textoBotones);
        }else{
            fechaf=fechaSelecionada;
            this.btnSeleccionarFechaFin.setText(textoBotones);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        fechaSelecionada.setHours(hourOfDay);
        fechaSelecionada.setMinutes(minute);
    }
}