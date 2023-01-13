package com.example.viviappis.data.model.recicleView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScorrimentoRanklist extends RecyclerView.Adapter<ScorrimentoRanklist.MyViewHolder>
{
    private Context context;
    private List<Utente> part;
    private String prop;

    public ScorrimentoRanklist(Context context, String prop)
    {
        this.context=context;
        part = new ArrayList<>();
        this.prop = prop;
    }


    public void addPart(Utente e){part.add(e);}

    public void sort()
    {
        part.sort((t,  t1)->
        {
            long i = t.getScore();
            long ii = t1.getScore();

            return i>ii ? -1 : i<ii ? 1 : 0;
        });
    }


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
        if (part.get(position).getEmail().equals(prop))
        {
            holder.c.setCardBackgroundColor(context.getColor(R.color.giallocra));
            holder.nomeUtente.setText("Tu");
        }
        else
        {
            holder.c.setCardBackgroundColor(0);
            holder.nomeUtente.setText(part.get(position).getUsername());
        }

        holder.userScore.setText(part.get(position).getScore()+"");
        holder.pos.setText((position+1)+"");

    }

    @Override
    public int getItemCount() {return part.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView nomeUtente ,userScore, pos;
        CardView c;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nomeUtente = itemView.findViewById(R.id.ranklistName);
            userScore = itemView.findViewById(R.id.ranklistPoint);
            pos = itemView.findViewById(R.id.ranklistPos);
            c = itemView.findViewById(R.id.cardRanklist);

        }
    }
}
