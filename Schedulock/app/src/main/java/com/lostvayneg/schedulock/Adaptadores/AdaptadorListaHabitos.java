package com.lostvayneg.schedulock.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lostvayneg.schedulock.Entidades.Habito;
import com.lostvayneg.schedulock.R;

import java.util.ArrayList;

public class AdaptadorListaHabitos extends RecyclerView.Adapter<AdaptadorListaHabitos.ViewHolder> implements View.OnClickListener{

    private LayoutInflater inflater;
    private ArrayList<Habito> model;
    private StorageReference referenciaSBD;
    private FirebaseStorage storageBD;
    private onHabitoListener mOnHabitoListener;
    private View.OnClickListener listener;

    public AdaptadorListaHabitos(Context context, ArrayList<Habito> model, onHabitoListener onUsuarioListener){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
        this.storageBD = FirebaseStorage.getInstance();
        this.mOnHabitoListener= onUsuarioListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_habito, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view,mOnHabitoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

        TextView nombre;
        ProgressBar progreso;
        CheckBox lunes;
        CheckBox martes;
        CheckBox miercoles;
        CheckBox jueves;
        CheckBox viernes;
        CheckBox sabado;
        CheckBox domingo;
        onHabitoListener onHabitoListener;
        public ViewHolder(@NonNull View itemView, final onHabitoListener onHabitoListener) {
            super(itemView);
            nombre = itemView.findViewById(R.id.nombre_habito_ver_habito);
            progreso= itemView.findViewById(R.id.progreso_habito_ver_habito);
            lunes=itemView.findViewById(R.id.checkB_lunes_ver_habito);
            martes=itemView.findViewById(R.id.checkB_martes_ver_habito);
            miercoles= itemView.findViewById(R.id.checkB_miercoles_ver_habito);
            jueves= itemView.findViewById(R.id.checkB_jueves_ver_habito);
            viernes= itemView.findViewById(R.id.checkB_viernes_ver_habito);
            sabado= itemView.findViewById(R.id.checkB_sabado_ver_habito);
            domingo= itemView.findViewById(R.id.checkB_domingo_ver_habito);
            this.onHabitoListener=onHabitoListener;
        }
    }
    public void setOnClickListener(View.OnClickListener listener)
    {
        this.listener = listener;
    }
    public interface onHabitoListener{
        void onHabitoClick(int position);

    }
}
