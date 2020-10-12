package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lostvayneg.schedulock.Entidades.Calendario;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

public class AdaptadorListaCalendarios extends RecyclerView.Adapter<AdaptadorListaCalendarios.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Calendario> model;

    //listener
    private View.OnClickListener listener;

    public AdaptadorListaCalendarios(Context context, ArrayList<Calendario> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_calendario, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String etiqueta = model.get(position).getEtiqueta();
        holder.nombre.setText(nombre);
        holder.etiqueta.setText(etiqueta);
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

        TextView nombre, etiqueta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.tv_nom_calendario);
            etiqueta = itemView.findViewById(R.id.tv_etiqueta);
        }
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

}
