package com.lazyswift.schedulock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lazyswift.schedulock.Modelo.Calendario;
import com.lazyswift.schedulock.R;

import java.util.ArrayList;

public class AdapterCalendario extends RecyclerView.Adapter<AdapterCalendario.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Calendario> model;

    //listener
    private View.OnClickListener listener;

    public AdapterCalendario(Context context, ArrayList<Calendario> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_calendario, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).nombre;
        String etiqueta = model.get(position).etiqueta;
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
