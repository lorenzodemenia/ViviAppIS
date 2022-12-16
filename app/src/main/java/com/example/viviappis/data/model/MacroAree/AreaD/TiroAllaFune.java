package com.example.viviappis.data.model.MacroAree.AreaD;

import com.example.viviappis.data.model.Utente;

public class TiroAllaFune extends MacroD{

    public TiroAllaFune(String name, String creator, String date, String password, boolean isPublic,String nameSquadra1, String nameSquadra2, int points)  {
        super(name, "Il tiro alla fune prevede l'utilizzo di una fune di lunghezza variabile in base al numero di partecipanti. Le squadre che si sfidano al tiro alla fune devono essere composte da almeno 5 persone, anche se la regola suggerisce un numero di 8 persone per squadra. L'obbiettivo è riuscire a tirare l'altra squadra oltre una linea posta a metà tra le due posizioni di partenza.",
                creator, date, password, isPublic, 10, 40, nameSquadra1, nameSquadra2, points);
    }
}
