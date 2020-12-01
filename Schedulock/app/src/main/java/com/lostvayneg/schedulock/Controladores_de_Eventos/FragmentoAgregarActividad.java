package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.Entidades.Localizacion;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Servicios.Recibir_Alarma;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;
import com.lostvayneg.schedulock.Servicios.notificacionService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentoAgregarActividad extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button btnSeleccionarFechaInicio;
    private Button btnSeleccionarFechaFin;
    private Button btnGuardarActividad;
    private Button btnagregarUsuario;
    private View pantalla;
    private Spinner importancia,recordatorio,frecuencia;
    private Actividad actividad;
    private Double latitud=null;
    private Double longitud=null;
    private int contfecha;
    private Date fechaI,fechaf, fechaSelecionada;
    private EditText nombre,categoria,descripcion,ubicacion,newU;
    private Geocoder mGeocoder;
    private Spinner spinner;
    private String categotiab;
    private Acceso_Base_Datos baseDatos;
    private Calendario calendarioUsuario;
    public static String CHANNEL_ID = "MyApp";
    ArrayList<String> arregloInvitadosEjemplo=new ArrayList<>();
    private AlarmManager manejadorAlarmas;

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
        final ArrayList<String> arregloCategorias =new ArrayList<String>() ;
        arregloCategorias.add("Personal");
        arregloCategorias.add("Laboral");
        arregloCategorias.add("Academico");
        arregloCategorias.add("Ocio");
        arregloCategorias.add("Otro");
        createNotificationChannel();
        ArrayAdapter adp=new ArrayAdapter(pantalla.getContext(), android.R.layout.simple_spinner_dropdown_item,arregloCategorias);
        spinner=pantalla.findViewById(R.id.act_categoria);
        spinner.setAdapter(adp);
        calendarioUsuario=(Calendario) getArguments().getSerializable("calendar");
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
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==4){
                    categoria.setVisibility(View.VISIBLE);
                }else{
                    categotiab=arregloCategorias.get(position);
                    categoria.setVisibility(View.INVISIBLE);
                    categoria.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> adaptadorInvitados = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_list_item_1,arregloInvitadosEjemplo);
        final ListView listaUsuarios = pantalla.findViewById(R.id.listaUsuarios);
        listaUsuarios.setVisibility(View.INVISIBLE);
        listaUsuarios.setVisibility(View.GONE);
        newU=pantalla.findViewById(R.id.newUser);
        btnagregarUsuario=pantalla.findViewById(R.id.agragarUsuario);
        btnagregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                arregloInvitadosEjemplo.add(newU.getText().toString());
                ArrayAdapter<String> adaptadorInvitados = new ArrayAdapter<String>(getContext(),  android.R.layout.simple_list_item_1,arregloInvitadosEjemplo);
                newU.setText("");
                listaUsuarios.setAdapter(adaptadorInvitados);
                listaUsuarios.setVisibility(View.VISIBLE);
            }
        });
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
            actividad.setCategoria(categotiab);
            actividad.setInicio(fechaI);
            actividad.setFin(fechaf);
            actividad.setDescripcion(descripcion.getText().toString());
            actividad.setRecordatorio(recordatorio.getSelectedItem().toString());
            actividad.setPrioridad(importancia.getSelectedItem().toString());
            actividad.setFrencuenciaR(frecuencia.getSelectedItem().toString());
            actividad.setInvitado(false);
            actividad.setIdCalendario(calendarioUsuario.getIdCalendario());
            if(latitud != null && longitud != null){
                actividad.setLocalizacion(new Localizacion(latitud, longitud));
            }

            // Agregar Colaboradores
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference refAux = db.getReference("usuarios/");
            refAux.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<String> ids = new ArrayList<>();
                    for (DataSnapshot data: dataSnapshot.getChildren()) {
                        Usuario u = data.getValue(Usuario.class);
                        for (String correo: arregloInvitadosEjemplo) {
                            //Log.i("EROOOOO", correo);
                            if(u.getEmail().equals(correo)) {
                                ids.add(data.getKey());
                                //Log.i("EROOOOO", "ID: " + data.getKey());
                                break;
                            }
                        }
                    }

                    actividad.setColaboradores(ids);

                    baseDatos.agregarNuevaActividad(actividad);
                    Toast.makeText(pantalla.getContext(), "Se agrego la actividad", Toast.LENGTH_LONG).show();
                    Bundle enviarActividad = new Bundle();
                    enviarActividad.putSerializable("actividad", actividad);
                    crearRecordatorio(getContext(), actividad);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("calendar",(Serializable)calendarioUsuario);
                    Intent intent = new Intent(getContext(), notificacionService.class);
                    intent.putExtra("lista",arregloInvitadosEjemplo);
                    notificacionService.enqueueWork(getContext(), intent);
                    Navigation.findNavController(getView()).navigate(R.id.ir_de_agregar_actividad_a_ver_actividad, enviarActividad);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }

    private void crearRecordatorio(Context context, Actividad actividad) {
        Log.i("Alarma", "create an alarm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, fechaI.getYear());
        calendar.set(Calendar.MONTH, fechaI.getMonth()-1);
        calendar.set(Calendar.DAY_OF_MONTH, fechaI.getDate());
        int minutos = fechaI.getMinutes();
        switch (recordatorio.getSelectedItem().toString()) {
            case "Sin Recordatorio":
                return;
            case "1 Minuto Antes":
                minutos-=1;
                if(minutos < 0){
                    minutos = 59 + minutos;
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-1);
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours());
                }
                break;
            case "5 Minutos Antes":
                minutos-=5;
                if(minutos < 0){
                    minutos = 59 + minutos;
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-1);
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours());
                }
                break;
            case "10 Minutos Antes":
                minutos-=10;
                if(minutos < 0){
                    minutos = 59 + minutos;
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-1);
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours());
                }
                break;
            case "30 Minutos Antes":
                minutos-=30;
                if(minutos < 0){
                    minutos = 59 + minutos;
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-1);
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours());
                }
                break;
            case "1 Hora Antes":
                calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-1);
                break;
            case "2 Horas Antes":
                calendar.set(Calendar.HOUR_OF_DAY, fechaI.getHours()-2);
                break;
        }

        calendar.set(Calendar.MINUTE, minutos);
        manejadorAlarmas = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent recibirAlarma = new Intent(context, Recibir_Alarma.class);
        Bundle datosActividad = new Bundle();
        final int id_intent = (int) System.currentTimeMillis();
        datosActividad.putString("nombre_actividad", actividad.getNombre());
        datosActividad.putSerializable("inicio_actividad", actividad.getInicio());
        datosActividad.putInt("id_pending_intent", id_intent);
        recibirAlarma.putExtras(datosActividad);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id_intent, recibirAlarma,
                0);

        long frecuenciaRecordatorio = 0;

        Log.i("AlarmaActual", ""+ System.currentTimeMillis());
        Log.i("AlarmaCalendario",""+ calendar.getTimeInMillis());
        Log.i("AlarmaDiferencia", "" + (calendar.getTimeInMillis()-System.currentTimeMillis()));
        Log.i("AlarmaMinutos",""+minutos);
        switch (frecuencia.getSelectedItem().toString()){
            case "Una Sola Vez":
                manejadorAlarmas.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                return;
            case "Cada Minuto":
                frecuenciaRecordatorio = 1*60*1000;
                break;
            case "Cada 5 Minutos":
                frecuenciaRecordatorio = 5*60*1000;
                break;
            case "Cada 10 Minutos":
                frecuenciaRecordatorio = 10*60*1000;
                break;
            case "Cada 20 Minutos":
                frecuenciaRecordatorio = 20*60*1000;
                break;
            case "Cada 30 Minutos":
                frecuenciaRecordatorio = AlarmManager.INTERVAL_HALF_HOUR;
                break;
            default:
                frecuenciaRecordatorio = 0;
        }
        manejadorAlarmas.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),frecuenciaRecordatorio, pendingIntent);
    }
    private boolean verificarCampos(){

        if(!nombre.getText().toString().isEmpty()){

            if(spinner.getSelectedItemPosition()>=0){
                if(spinner.getSelectedItem().toString().equals("otro")){
                    categotiab=categoria.getText().toString();
                }
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
                Toast.makeText(pantalla.getContext(), "Seleccione la categoria de la actividad", Toast.LENGTH_LONG).show();
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
                Toast.makeText(pantalla.getContext(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(pantalla.getContext(), "La dirección esta vacía", Toast.LENGTH_SHORT).show();}
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
    private void createNotificationChannel() {
// Create the NotificationChannel, but only on API 26+ because
// the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "channel description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//IMPORTANCE_MAX MUESTRA LA NOTIFICACIÓN ANIMADA
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
// Register the channel with the system; you can't change the importance
// or other notification behaviors after this
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}