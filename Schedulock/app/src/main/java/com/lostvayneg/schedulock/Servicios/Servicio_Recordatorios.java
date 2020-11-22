package com.lostvayneg.schedulock.Servicios;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Entidades.Actividad;

import java.util.Date;

public class Servicio_Recordatorios extends Service {

    private static final String TAG = "Servicio_Recordatorios";
    public static final String RUTA_ACTIVIDADES ="actividades/";
    public DatabaseReference referenciaBD;
    public FirebaseDatabase baseDatos;

    public Servicio_Recordatorios() {
        crearCanalNotificacion();
        baseDatos=FirebaseDatabase.getInstance();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() , service started...");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        referenciaBD = baseDatos.getReference(RUTA_ACTIVIDADES + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/");
        referenciaBD.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot actividad: dataSnapshot.getChildren()) {
                    Actividad act = actividad.getValue(Actividad.class);

                    Date fechaActual = new Date();
                    if(act.getInicio().compareTo(fechaActual) > 0){
                        Log.i("ASD", fechaActual.toString());
                        Intent recibirAlarma = new Intent(getBaseContext(), Recibir_Alarma.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, recibirAlarma, 0);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        long tiempoAlIniciar = System.currentTimeMillis();
                        long segundosDespues = act.getInicio().getTime() - fechaActual.getTime();

                        alarmManager.set(AlarmManager.RTC_WAKEUP,tiempoAlIniciar + segundosDespues, pendingIntent);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return Service.START_STICKY;
    }

    public IBinder onUnBind(Intent arg0) {
        Log.i(TAG, "onUnBind()");
        return null;
    }

    public void onStop() {
        Log.i(TAG, "onStop()");
    }
    public void onPause() {
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onCreate() , service stopped...");
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory()");
    }

    private void crearCanalNotificacion(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence nombre = "Schedulock";
            String descripcion = "Canal para Schedulock";
            int importancia = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel canal = new NotificationChannel("notificacionSchedulock", nombre, importancia);
            canal.setDescription(descripcion);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(canal);
        }
    }

}
