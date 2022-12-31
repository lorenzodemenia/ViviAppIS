package com.example.viviappis.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;

import java.util.ArrayList;

public class ScorrimentoDashboard extends RecyclerView.Adapter<ScorrimentoDashboard.MyViewHolder> {
    Context context;
    ArrayList<Evento> eventos;

    public ScorrimentoDashboard(Context context, ArrayList<Evento> eventos){
        this.context=context;
        this.eventos=eventos;
    }

    @NonNull
    @Override
    public ScorrimentoDashboard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout della riga evento
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view= layoutInflater.inflate(R.layout.single_card_event, parent, false);
        return new ScorrimentoDashboard.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ScorrimentoDashboard.MyViewHolder holder, int position) {
        //posizione delle righe nella dash
        holder.titolo.setText(eventos.get(position).getName());
        holder.data.setText(eventos.get(position).getDate());
        holder.luogo.setText("Posizione coming soon"); //LA CLASSE EVENTO NON HA UNA STRINGA LUOGO
        holder.imageView.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);
    }

    @Override
    public int getItemCount() {
        //numero degli eventi mostrati
        return eventos.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //prende la vista del file layout
        ImageView imageView;
        TextView titolo, data, luogo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            titolo =itemView.findViewById(R.id.textView);
            data = itemView.findViewById(R.id.textView2);
            luogo = itemView.findViewById(R.id.textView3);
        }
    }
}
