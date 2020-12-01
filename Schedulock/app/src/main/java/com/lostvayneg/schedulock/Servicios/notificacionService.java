package com.lostvayneg.schedulock.Servicios;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Controladores_de_Eventos.FragmentoAgregarActividad;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class notificacionService extends JobIntentService{


    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    DatabaseReference myRef;
    FirebaseDatabase database;
    public static final String RUTA_USUARIOS ="usuarios/";
    private FirebaseAuth mAuth;
    // TODO: Rename parameters

    private static final int JOB_ID = 12;
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, notificacionService.class, JOB_ID, intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        database= FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        myRef=database.getReference(RUTA_USUARIOS);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        ArrayList<String> arregloInvitados = (ArrayList<String>) intent.getStringArrayListExtra("lista");
        mandarInvitacion(arregloInvitados);
    }

    private void mandarInvitacion(final ArrayList<String> arregloInvitados) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Usuario usuarioAux = singleSnapshot.getValue(Usuario.class);

                    for(int i=0;i<arregloInvitados.size();i++){
                        if(arregloInvitados.get(i).equals(usuarioAux.getEmail())){
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), FragmentoAgregarActividad.CHANNEL_ID);
                            mBuilder.setSmallIcon(R.drawable.common_full_open_on_phone);
                            mBuilder.setContentTitle("Invitacion a actividad");
                            mBuilder.setContentText(" Se le ha invitado a colaborar en una actividad");
                            mBuilder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                            //Intent intent = new Intent(getBaseContext(), SeguimientoActivity.class);
                           // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                   // Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //intent.putExtra("id",id);
                            //PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, null, 0);
                            //mBuilder.setContentIntent(pendingIntent);
                            mBuilder.setAutoCancel(true);
                            int notificationId = 001;
                            NotificationManagerCompat notificationManager =
                                    NotificationManagerCompat.from(getBaseContext());
                            notificationManager.notify(notificationId, mBuilder.build());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}