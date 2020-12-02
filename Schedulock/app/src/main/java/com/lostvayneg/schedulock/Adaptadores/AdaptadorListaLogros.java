package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lostvayneg.schedulock.Entidades.Logro;
import com.lostvayneg.schedulock.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdaptadorListaLogros extends  RecyclerView.Adapter<AdaptadorListaLogros.ViewHolder> implements View.OnClickListener{
    private LayoutInflater inflater;
    private ArrayList<Logro> model;
    private StorageReference referenciaSBD;
    private FirebaseStorage storageBD;
    public static final String RUTA_LOGROS = "logros_usuario/";

    //listener
    private View.OnClickListener listener;

    public AdaptadorListaLogros(Context context, ArrayList<Logro> model) {
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }
        @NonNull
        @Override

        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_logro, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }
//Acomodar
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String titulo = model.get(position).getTitulo();
        String descripcion= model.get(position).getDescripcion();
        long puntos=model.get(position).getPuntos();
        holder.imagen.setImageResource(R.drawable.ic_trofeo_logro);
        holder.titulo.setText(titulo);
        holder.descripcion.setText(descripcion);
        holder.puntos.setText((int) puntos);
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

        ImageView imagen;
        TextView titulo;
        TextView puntos;
        TextView descripcion;
        ProgressBar completado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_premio_logro_ver_logro);
            titulo=itemView.findViewById(R.id.titulo_logro_ver_logro);
            puntos=itemView.findViewById(R.id.puntos_logro_ver_logro);
            descripcion= itemView.findViewById(R.id.descripcion_logro_ver_logro);
        }
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

}
