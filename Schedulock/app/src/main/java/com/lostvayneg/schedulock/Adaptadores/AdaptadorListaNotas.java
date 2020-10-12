package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lostvayneg.schedulock.Entidades.Nota;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

public class AdaptadorListaNotas extends RecyclerView.Adapter<AdaptadorListaNotas.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Nota> model;

    //listener
    private View.OnClickListener listener;

    public AdaptadorListaNotas(Context context, ArrayList<Nota> model){
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
        String actividad = model.get(position).getActividad() != null ? model.get(position).getActividad().getNombre() : "";

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
