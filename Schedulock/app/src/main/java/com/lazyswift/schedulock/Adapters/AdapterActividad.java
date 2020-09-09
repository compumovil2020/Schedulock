package com.lazyswift.schedulock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lazyswift.schedulock.Modelo.Actividad;
import com.lazyswift.schedulock.Modelo.Calendario;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class AdapterActividad extends RecyclerView.Adapter<AdapterActividad.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Actividad> model;

    //listener
    private View.OnClickListener listener;

    public AdapterActividad(Context context, ArrayList<Actividad> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_actividad, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).nombre;
        String categoria = model.get(position).categoria;
        String fechaInicio = model.get(position).inicio;
        String fechaFin = model.get(position).fin;

        holder.nombre.setText(nombre);
        holder.categoria.setText(categoria);
        holder.inicio.setText(fechaInicio);
        holder.fin.setText(fechaFin);
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

        TextView nombre, categoria, inicio, fin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tv_nom_actividad);
            categoria = itemView.findViewById(R.id.tv_categoria);
            inicio = itemView.findViewById(R.id.tv_inicio_fecha);
            fin = itemView.findViewById(R.id.tv_fin_fecha);
        }
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

}
