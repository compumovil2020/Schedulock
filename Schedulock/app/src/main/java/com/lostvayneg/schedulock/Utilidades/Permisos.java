package com.lostvayneg.schedulock.Utilidades;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Permisos {

    public Permisos() {
    }

    public boolean solicitarPermisoSeleccionarImagen(Activity context , String permiso, String justificacion, int idPermiso){

        if (ContextCompat.checkSelfPermission(context,permiso) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)) {
            }
            ActivityCompat.requestPermissions(context,new String[]{permiso}, idPermiso);
        }
        else{
            return true;
        }
        return false;
    }
}
