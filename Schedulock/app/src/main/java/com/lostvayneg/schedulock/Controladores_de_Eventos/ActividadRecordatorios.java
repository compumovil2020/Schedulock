package com.lostvayneg.schedulock.Controladores_de_Eventos;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Servicios.Recibir_Alarma;

public class ActividadRecordatorios extends AppCompatActivity {

    private TextView nombre_actividad;
    private TextView hora_inicio_activdad;
    private Button parar_recordario;
    private Button posponer_recordatorio;
    private Bundle datosNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_recordatorios);
        nombre_actividad = findViewById(R.id.nombre_actividad_recordatorio);
        hora_inicio_activdad = findViewById(R.id.hora_inicio_actividad_recordatorio);
        parar_recordario = findViewById(R.id.btn_parar_recordatorios_recordatorio);
        posponer_recordatorio = findViewById(R.id.btn_posponer_recordatorios_recordatorio);

        datosNotificacion = getIntent().getExtras();

        nombre_actividad.setText(datosNotificacion.getString("nombre_actividad"));
        hora_inicio_activdad.setText(datosNotificacion.getString("hora_inicio_actividad"));

        parar_recordario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_intent = datosNotificacion.getInt("id_pending_intent");
                Intent recibirAlarma = new Intent(getBaseContext(), Recibir_Alarma.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id_intent, recibirAlarma,
                        0);
                if(pendingIntent != null){
                    AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                    Log.i("Alarma", "Se cancelo la alarma creada con el id " + id_intent);
                    finish();
                }
            }
        });

        posponer_recordatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(v.getContext(), Login.class);
                startActivity(login);
            }
        });

    }
}