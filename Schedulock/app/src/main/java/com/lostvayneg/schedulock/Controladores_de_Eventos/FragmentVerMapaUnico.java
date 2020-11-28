package com.lostvayneg.schedulock.Controladores_de_Eventos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Localizacion;
import com.lostvayneg.schedulock.Entidades.Usuario;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FragmentVerMapaUnico extends Fragment {

    public FirebaseDatabase fireDB;
    public HashMap <String, DatabaseReference> referencias;
    public HashMap <String, ValueEventListener> suscripciones;
    public HashMap <String, Marker> markers;
    public DatabaseReference refDBUser;
    private FirebaseAuth authF;
    private FirebaseUser fireUser;
    private GoogleMap mMap;
    public static final String RUTA_USUARIOS ="usuarios/";

    public static final int FINE_LOCATION = 2;
    public static final int REQUEST_CHECK_SETTINGS = 3;
    private static final double RADIUS_OF_EARTH_KM = 6371;

    public FusedLocationProviderClient locationProvider;
    public LocationRequest locationRequest;
    public LocationCallback locationCallback;

    public SensorManager sensorManager;
    public Sensor lightSensor;
    public SensorEventListener lightSensorListener;

    public Geocoder mGeocoder;
    public Marker actualMarker = null;
    public Marker actividadMarker = null;

    public EditText searchMap;

    public int inicio = 0;
    public boolean pausado = false;

    public ImageView location;

    private Actividad actividad;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    Toast.makeText(getActivity(), "Distancia: " +
                                    distance(actualMarker.getPosition().latitude, actualMarker.getPosition().longitude, marker.getPosition().latitude, marker.getPosition().longitude) +" Km",
                            Toast.LENGTH_LONG).show();
                    marker.showInfoWindow();
                    return true;
                }
            });

            mMap.getUiSettings().setZoomGesturesEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);

            actividadMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(actividad.getLocalizacion().getLatitud(), actividad.getLocalizacion().getLongitud())
                        ).title(actividad.getNombre()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            seguirInvitados();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ver_mapa_unico, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        actividad = (Actividad) getArguments().getSerializable("actividad");

        super.onViewCreated(view, savedInstanceState);

        fireDB = FirebaseDatabase.getInstance();
        authF = FirebaseAuth.getInstance();
        fireUser = authF.getCurrentUser();
        refDBUser = fireDB.getReference(RUTA_USUARIOS + fireUser.getUid());

        location = getView().findViewById(R.id.imageLocation);

        suscripciones = new HashMap<>();
        referencias = new HashMap<>();
        markers = new HashMap<>();

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moverDondeEstoy(view);
            }
        });

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        searchMap = getView().findViewById(R.id.searchMap);
        mGeocoder = new Geocoder(getActivity());

        //-------------------------------------------------------------------------------

        // Localización

        locationProvider = LocationServices.getFusedLocationProviderClient(getActivity());
        locationRequest = createLocationRequest();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {

                    if(actualMarker != null) {
                        actualMarker.remove();
                    }

                    LatLng actualLoc = new LatLng(location.getLatitude(), location.getLongitude());
                    updateLocationUsuario(actualLoc);
                    actualMarker = mMap.addMarker(new MarkerOptions().position(actualLoc).title("Posición Actual").icon(BitmapDescriptorFactory
                            .fromBitmap(resizeMapIcons("person_blue", 70, 120))));

                    if (inicio == 0) {
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actualLoc, 12));
                        inicio = 1;
                    }
                }

            }
        };

        //-------------------------------------------------------------------------------

        // Sensores

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (mMap != null) {
                    if (event.values[0] < 5000) {
                        Log.i("MAPS", "DARK MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_map_night));
                    } else {
                        Log.i("MAPS", "LIGHT MAP " + event.values[0]);
                        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getActivity(), R.raw.style_map_day));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };

        //-------------------------------------------------------------------------------

        // Busqueda

        searchMap.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String addressString = searchMap.getText().toString();
                    if (!addressString.isEmpty()) {
                        try {
                            List<Address> addresses = mGeocoder.getFromLocationName(addressString, 2);
                            if (addresses != null && !addresses.isEmpty()) {
                                Address addressResult = addresses.get(0);
                                LatLng position = new LatLng(addressResult.getLatitude(), addressResult.getLongitude());
                                if (mMap != null) {
                                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
                                }
                            } else {
                                Toast.makeText(getActivity(), "Dirección no encontrada", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getActivity(), "La dirección esta vacía", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });

        //-------------------------------------------------------------------------------

        // Permisos

        solicitarPermiso(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, "Se necesita acceso a la localización", FINE_LOCATION);
        usePermission();

    }

    public void obtenerLocalizacionSeguir(final String uid){
        DatabaseReference refUsr = fireDB.getReference(RUTA_USUARIOS + uid);
        ValueEventListener vEL = refUsr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Usuario usr = datasnapshot.getValue(Usuario.class);
                if(markers.get(uid) != null) {
                    markers.get(uid).remove();
                }

                LatLng actualLoc = new LatLng(usr.getLocalizacion().getLatitud(), usr.getLocalizacion().getLongitud());
                double distancia = distance(usr.getLocalizacion().getLatitud(), usr.getLocalizacion().getLongitud(), actualMarker.getPosition().latitude, actualMarker.getPosition().longitude);
                Marker aux = mMap.addMarker(new MarkerOptions().position(actualLoc).title(usr.getNombre()+" a: " + distancia + " Km"));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(aux.getPosition(), 12));
                markers.put(uid, aux);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        referencias.put(uid, refUsr);
        suscripciones.put(uid, vEL);
    }

    public void updateLocationUsuario(final LatLng localizacion) {
        refDBUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                Usuario usr = datasnapshot.getValue(Usuario.class);
                usr.setLocalizacion(new Localizacion(localizacion.latitude, localizacion.longitude));
                refDBUser.setValue(usr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void seguirInvitados() {

        for (String id: actividad.getColaboradores()) {
            if (!fireUser.getUid().equals(id))
                obtenerLocalizacionSeguir(id);
        }

        // TODO: CREADOR PARA LOS INVITADOS
        if(!fireUser.getUid().equals(actividad.getIdUser())){
            obtenerLocalizacionSeguir(actividad.getIdUser());
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        //Suscribirse a actualizaciones de Localización
        startLocationUpdates();

        //Suscribirse a actualizaciones de Sensores
        sensorManager.registerListener(lightSensorListener, lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        if(pausado) {
            for (String id: referencias.keySet()) {
                referencias.get(id).addValueEventListener(suscripciones.get(id));
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        //Desuscribirse a actualizaciones de Localización
        stopLocationUpdates();

        //Desuscribirse a actualizaciones de Sensores
        sensorManager.unregisterListener(lightSensorListener);

        //Desuscribirse a los usuarios seguidos
        for (String id: referencias.keySet()) {
            referencias.get(id).removeEventListener(suscripciones.get(id));
        }

        pausado = true;
    }

    // Función para especificar la petición secuencial de la localización
    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }

    // Función para solicitar el permiso de la localización
    private void solicitarPermiso(Activity context, String permiso, String justificacion, int idPermiso)
    {
        if(ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, permiso)){
                Toast.makeText(getActivity(), justificacion, Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(context, new String[]{permiso}, idPermiso);
        }
    }

    // Función para verificar que ya hay permisos
    private void usePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            checkSettings();
        } else {
            Toast.makeText(getActivity(), "Para esta funcionalidad es necesario que de permisos a la localización", Toast.LENGTH_SHORT);
        }
    }

    // Función para validar que el GPS está encendido
    private void checkSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED: {
                        try{
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        }
                        catch (IntentSender.SendIntentException sendEx) {
                            Log.e("LocationAPP", sendEx.getMessage());
                        }
                        break;
                    }
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    // Función para suscribirse a actualizaciones de localización
    private void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationProvider.requestLocationUpdates(locationRequest, locationCallback, null);
        } else {
            Toast.makeText(getActivity(),"Para esta funcionalidad es necesario que de permisos a la localización", Toast.LENGTH_SHORT);
        }
    }

    // Función para Desuscribirse a actualizaciones de localización
    private void stopLocationUpdates() {
        locationProvider.removeLocationUpdates(locationCallback);
    }

    // Función que recibe el resultado del permiso aceptado
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case FINE_LOCATION: {
                usePermission();
                break;
            }
        }
    }

    // Función que recibe la respuesta de lanzar una actividad
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CHECK_SETTINGS: {
                if (resultCode == Activity.RESULT_OK) {
                    startLocationUpdates();
                } else {
                    Log.e("LocationAPP", "Sin acceso a localización, hardware deshabilitado");
                }
                break;
            }
        }

    }

    // Función que calcula la distancia entre dos coordenadas
    public double distance(double lat1, double long1, double lat2, double long2) {
        double latDistance = Math.toRadians(lat1-lat2);
        double longDistance = Math.toRadians(long1-long2);

        double a = Math.sin(latDistance/2) * Math.sin(latDistance/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(longDistance/2) * Math.sin(longDistance/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double result = RADIUS_OF_EARTH_KM * c;
        return Math.round(result*100.0)/100.0;

    }

    // Función para centrar la cámara en la posición del usuario
    public void moverDondeEstoy(View v) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(actualMarker.getPosition(), 15));
    }

    // Función para cambiar el tamaño a una imagen
    public Bitmap resizeMapIcons(String iconName, int width, int height) {
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}