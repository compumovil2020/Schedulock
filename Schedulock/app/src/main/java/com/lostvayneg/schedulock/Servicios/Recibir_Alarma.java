package com.lostvayneg.schedulock.Servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.lostvayneg.schedulock.Controladores_de_Eventos.ActividadRecordatorios;
import com.lostvayneg.schedulock.R;

import java.util.Date;

public class Recibir_Alarma extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle datosActividad = intent.getExtras();
        String nombre_actividad = datosActividad.getString("nombre_actividad");
        Date inicio_actividad = (Date) datosActividad.getSerializable("inicio_actividad");
        String hora_inicio = inicio_actividad.getHours() + ":" + inicio_actividad.getMinutes();
        int id_intent = datosActividad.getInt("id_pending_intent");
        creaNotificacion(id_intent, nombre_actividad, hora_inicio, context);
    }

    public static void creaNotificacion(int intentAlarma, String title, String content, Context context) {
        try {

            Intent notificationIntent = new Intent(context, ActividadRecordatorios.class);
            Bundle datosNotificacion = new Bundle();
            datosNotificacion.putInt("id_pending_intent", intentAlarma);
            datosNotificacion.putString("nombre_actividad", title);
            datosNotificacion.putString("hora_inicio_actividad",content);
            notificationIntent.putExtras(datosNotificacion);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setContentTitle("Recordatorio: " + title)
                    .setContentText("La actividad inicia a las " + content)
                    .setSmallIcon(R.drawable.icono_estrella_trofeo)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);

            notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());


        } catch (Exception e) {
            Log.e("ASD", "crearNotificaciones:" + e.getMessage());
        }
    }
}