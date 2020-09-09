package com.lazyswift.schedulock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lazyswift.schedulock.Modelo.Actividad;
import com.lazyswift.schedulock.Modelo.Nota;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class AdapterNota extends RecyclerView.Adapter<AdapterNota.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Nota> model;

    //listener
    private View.OnClickListener listener;

    public AdapterNota(Context context, ArrayList<Nota> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_nota, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).nombre;
        String actividad = model.get(position).actividad != null ? model.get(position).actividad.nombre : "";

        holder.nombre.setText(nombre);
        holder.actividad.setText(actividad);
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
