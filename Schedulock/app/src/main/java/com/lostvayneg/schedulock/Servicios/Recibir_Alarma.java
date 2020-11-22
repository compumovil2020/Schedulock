package com.lostvayneg.schedulock.Servicios;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.lostvayneg.schedulock.Controladores_de_Eventos.Login;
import com.lostvayneg.schedulock.R;

public class Recibir_Alarma extends BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        creaNotificacion(System.currentTimeMillis(), "Titulo de la notificacion", "Contenido de la notificacion", context);
    }

    public static void creaNotificacion(long when, String title, String content, Context context) {
        try {

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "notificacionSchedulock")
                    .setSmallIcon(R.drawable.icono_estrella_trofeo)
                    .setContentTitle("Recordatorio de Actividad")
                    .setContentText("Algo por decir")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            notificationManager.notify((int) when, notificationBuilder.build());


        } catch (Exception e) {
            Log.e("ASD", "crearNotificaciones:" + e.getMessage());
        }
    }
}