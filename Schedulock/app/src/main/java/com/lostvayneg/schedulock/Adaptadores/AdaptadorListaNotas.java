package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lostvayneg.schedulock.Entidades.Actividad;
import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.R;
import com.lostvayneg.schedulock.Utilidades.Acceso_Base_Datos;

import java.util.ArrayList;

public class AdaptadorListaNotas extends RecyclerView.Adapter<AdaptadorListaNotas.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Nota> model;
    private FirebaseDatabase fireDB;

    //listener
    private View.OnClickListener listener;

    public AdaptadorListaNotas(Context context, ArrayList<Nota> model){
        fireDB = FirebaseDatabase.getInstance();
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_nota, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String actividad = (model.get(position).getIdActividad() != null && model.get(position).getIdActividad().equals("")) ?
                consultarActividad(model.get(position).getIdActividad()).getNombre() : "";

        holder.nombre.setText(nombre);
        holder.actividad.setText(actividad);


    }

    public Actividad consultarActividad (String id) {
        DatabaseReference ref = fireDB.getReference(Acceso_Base_Datos.RUTA_ACTIVIDADES + id);
        final Actividad[] result = {null};
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Actividad act = dataSnapshot.getValue(Actividad.class);
                result[0] = act;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });
        return result[0];
    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre, actividad;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tv_nom_nota);
            actividad = itemView.findViewById(R.id.tv_actividad);
        }
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

}
