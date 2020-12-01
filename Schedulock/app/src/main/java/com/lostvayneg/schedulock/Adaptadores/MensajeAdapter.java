package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lostvayneg.schedulock.Entidades.Mensaje;
import com.lostvayneg.schedulock.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> implements View.OnClickListener {
    ArrayList<Mensaje> mensajes;
    LayoutInflater inflater;
    View.OnClickListener listener;

    public MensajeAdapter(Context c, ArrayList<Mensaje> mensajes) {
        this.inflater = LayoutInflater.from(c);
        this.mensajes= mensajes;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.fila_mensaje, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MensajeAdapter.ViewHolder holder, int position) {
        String nombre = mensajes.get(position).getNombreUsr();
        String mensaje = mensajes.get(position).getMensaje();
        holder.nombre.setText(nombre);
        holder.mensaje.setText(mensaje);
    }

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView mensaje;
        public ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.nombreUsr);
            mensaje = v.findViewById(R.id.mensaje);
        }
    }
}
