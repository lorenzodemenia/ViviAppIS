package com.example.viviappis.data.model.recicleView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viviappis.R;
import com.example.viviappis.data.model.Utente;

import java.util.ArrayList;
import java.util.List;

/**
 * Questa classe serve a gestire lo scorrimento della ranklist
 * @author jacopo
 * @version 1.0
 */
public class ScorrimentoRanklist extends RecyclerView.Adapter<ScorrimentoRanklist.MyViewHolder>
{
    private Context context;
    private List<Utente> utn;
    private String prop;

    /**
     * crea un oggetto di tipo ScorrimentoRanklist
     * @param context contex del oggetto
     * @param prop email dell'utente attualmente loggato
     */
    public ScorrimentoRanklist(Context context, String prop)
    {
        this.context=context;
        utn = new ArrayList<>();
        this.prop = prop;
    }


    /**
     * aggiunge un utente alla lista degli utenti
     * @param e utente da aggiungere
     */
    public void addUtn(Utente e){utn.add(e);}

    /**
     * ordina la lista degli utenti in base al loro punteggi
     */
    public void sort()
    {
        utn.sort((t, t1)->
        {
            long i = t.getScore();
            long ii = t1.getScore();

            return i>ii ? -1 : i<ii ? 1 : 0;
        });
    }

    /**
     * funzione chiamata quando viene creata la view del holder
     * @param parent gruppo di parentela
     * @param viewType tipo di view
     * @return ritorna l'holder creato
     */
    @NonNull
    @Override
    public ScorrimentoRanklist.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view=  LayoutInflater.from(context).inflate(R.layout.single_card_ranklist, parent, false);
        return new ScorrimentoRanklist.MyViewHolder(view);
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
        if (utn.get(position).getEmail().equals(prop))
        {
            holder.c.setCardBackgroundColor(context.getColor(R.color.giallocra));
            holder.nomeUtente.setText("Tu");
        }
        else
        {
            holder.c.setCardBackgroundColor(0);
            holder.nomeUtente.setText(utn.get(position).getUsername());
        }

        holder.userScore.setText(utn.get(position).getScore()+"");
        holder.pos.setText((position+1)+"");

    }

    /**
     * ritorna il numero massimo di elementi nell array degli utenti
     * @return ritorna la dimensione dell'array degli utenti
     */
    @Override
    public int getItemCount() {return utn.size();}

    /**
     * Questa classe serve a gestire un singolo holder per la dashbord
     * @author jacopo
     * @version 1.0
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        //prende la vista del file layout
        TextView nomeUtente ,userScore, pos;
        CardView c;

        /**
         * crea un oggetto di tipo MyViewHolder
         * @param itemView view che rappresenta oggetto
         */
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
