package com.example.viviappis.data.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.titolo.setText(part.get(position));
    }

    @Override
    public int getItemCount() {return part.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView titolo;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            titolo    = itemView.findViewById(R.id.cardPartNm);
        }
    }
}
