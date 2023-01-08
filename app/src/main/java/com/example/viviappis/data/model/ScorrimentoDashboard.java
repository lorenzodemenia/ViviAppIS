package com.example.viviappis.data.model;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.ui.event.EventPageFragment;
import com.example.viviappis.ui.event.NewEventFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScorrimentoDashboard extends RecyclerView.Adapter<ScorrimentoDashboard.MyViewHolder>
{
    private Context context;
    private List<Evento> eventos;
    private List<String> ids;
    private Fragment f;
    private Consumer<Boolean> func;

    private int conteiner;


    public ScorrimentoDashboard(Context context, Fragment dashboardFragment, Consumer<Boolean> func, boolean isDash)
    {
        this.context=context;
        this.eventos=new ArrayList<>();
        this.ids=new ArrayList<>();
        this.f = dashboardFragment;
        this.func = func;
        if(isDash) conteiner = R.id.dasboardFragment;
        else conteiner = R.id.homeFragment;
    }


    public void addEvent(Evento e, String id)
    {
        eventos.add(e);
        ids.add(id);
    }


    @NonNull
    @Override
    public ScorrimentoDashboard.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=  LayoutInflater.from(context).inflate(R.layout.single_card_event, parent, false);
        return new ScorrimentoDashboard.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //posizione delle righe nella dash
        holder.titolo.setText(eventos.get(position).getName());
        holder.data.setText(eventos.get(position).getDate());
        holder.luogo.setText(eventos.get(position).getLuogo()); //LA CLASSE EVENTO NON HA UNA STRINGA LUOGO
        holder.imageView.setImageResource(R.drawable.ic_baseline_arrow_back_ios_24);

        holder.imageView.setOnClickListener((v)->
        {
            FragmentTransaction t = f.getChildFragmentManager().beginTransaction();

            t.setReorderingAllowed(true);

            Bundle mex = new Bundle();
            mex.putString(f.getResources().getString(R.string.event_send_ev),ids.get(position));

            t.replace(conteiner, EventPageFragment.class ,mex);

            t.commit();
            func.accept(true);
        });
    }

    @Override
    public int getItemCount() {return eventos.size();}

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        ImageView imageView;
        TextView titolo, data, luogo;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.cardEvCont);
            titolo    = itemView.findViewById(R.id.cardEvNm);
            data      = itemView.findViewById(R.id.cardEvDate);
            luogo     = itemView.findViewById(R.id.cardEvLoc);
        }
    }
}
