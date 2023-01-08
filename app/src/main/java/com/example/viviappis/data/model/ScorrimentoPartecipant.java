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
import java.util.List;

public class ScorrimentoPartecipant extends RecyclerView.Adapter<ScorrimentoPartecipant.MyViewHolder>
{
    private Context context;
    private List<String> part;

    public ScorrimentoPartecipant(Context context)
    {
        this.context=context;
        part = new ArrayList<>();
    }


    public void addPart(String e){part.add(e);}


    @NonNull
    @Override
    public ScorrimentoPartecipant.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=  LayoutInflater.from(context).inflate(R.layout.single_card_partecipant, parent, false);
        return new ScorrimentoPartecipant.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //posizione delle righe nella dash
        holder.nomeUtente.setText(part.get(position));
        holder.avatar.setImageResource(R.drawable.ic_baseline_account_circle_24);

    }

    @Override
    public int getItemCount() {return part.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView nomeUtente;
        ImageView avatar;


        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nomeUtente = itemView.findViewById(R.id.nomeUtente);
            avatar = itemView.findViewById(R.id.avatar);

        }
    }
}
