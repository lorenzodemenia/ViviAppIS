package com.example.viviappis.data.model.recicleView;

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

/**
 * Questa classe serve a gestire lo scorrimento dei partecipanti ad un evento
 * @author jacopo
 * @version 1.0
 */
public class ScorrimentoPartecipant extends RecyclerView.Adapter<ScorrimentoPartecipant.MyViewHolder>
{
    private Context context;
    private List<String> part;

    /**
     * costruisce un oggetto di tipo ScorrimentoPartecipant
     * @param context contex del oggetto
     */
    public ScorrimentoPartecipant(Context context)
    {
        this.context=context;
        part = new ArrayList<>();
    }


    /**
     * aggiunge un partecipante all'evento visualizzato
     * @param e partecipante da aggiungere
     */
    public void addPart(String e){part.add(e);}

    /**
     * funzione chiamata quando viene creata la view del holder
     * @param parent gruppo di parentela
     * @param viewType tipo di view
     * @return ritorna l'holder creato
     */
    @NonNull
    @Override
    public ScorrimentoPartecipant.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=  LayoutInflater.from(context).inflate(R.layout.single_card_partecipant, parent, false);
        return new ScorrimentoPartecipant.MyViewHolder(view);
    }


    /**
     * modifica i valori del singolo holger quando viene visualizzato
     * @param holder holder dove inserire evento
     * @param position posizione dell'evento nell'array degli eventi
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //posizione delle righe nella dash
        holder.nomeUtente.setText(part.get(position));
        holder.avatar.setImageResource(R.drawable.ic_baseline_account_circle_24);

    }

    /**
     * ritorna il numero massimo di elementi nell array dei partecipanti
     * @return ritorna la dimensione dell'array di partecipanti
     */
    @Override
    public int getItemCount() {return part.size();}

    /**
     * Questa classe serve a gestire un singolo holder per la visualizzazione dei partecipanti
     * @author jacopo
     * @version 1.0
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView nomeUtente;
        ImageView avatar;


        /**
         * crea un oggetto di tipo MyViewHolder
         * @param itemView view che rappresenta oggetto
         */
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            nomeUtente = itemView.findViewById(R.id.nomeUtente);
            avatar = itemView.findViewById(R.id.avatar);

        }
    }
}
