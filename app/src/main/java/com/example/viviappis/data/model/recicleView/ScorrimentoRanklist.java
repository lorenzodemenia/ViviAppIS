package com.example.viviappis.data.model.recicleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;

import java.util.ArrayList;
import java.util.List;

public class ScorrimentoRanklist extends RecyclerView.Adapter<ScorrimentoRanklist.MyViewHolder>
{
    private Context context;
    private List<Utente> part;

    public ScorrimentoRanklist(Context context)
    {
        this.context=context;
        part = new ArrayList<>();
    }


    public void addPart(Utente e){part.add(e);}


    @NonNull
    @Override
    public ScorrimentoRanklist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=  LayoutInflater.from(context).inflate(R.layout.single_card_ranklist, parent, false);
        return new ScorrimentoRanklist.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //posizione delle righe nella dash
        holder.nomeUtente.setText(part.get(position).getUsername());
        holder.userScore.setText(part.get(position).getScore()+"");
        holder.pos.setText((position+1)+"");

    }

    @Override
    public int getItemCount() {return part.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView nomeUtente ,userScore, pos;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nomeUtente = itemView.findViewById(R.id.ranklistName);
            userScore = itemView.findViewById(R.id.ranklistPoint);
            pos = itemView.findViewById(R.id.ranklistPos);

        }
    }
}
