package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class Flipper extends MacroA {
    /**
     * Constructor of object MacroA
     *
     * @param name        name of the event
     * @param creator     user creator of the event
     * @param date        time of the event
     * @param password    password to log into the event
     * @param isPublic    define if the event is public or not
     * @param minPart     define the minimum number of partecipants
     * @param maxPart     define the maximum number of partecipants
     * @param points      points gained for the victory
     */
    public Flipper(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "Flipper è un gioco di abilità e di movimento, per poter giocare serve una palla." +
                "\nStando in piedi, i giocatori si dispongono in cerchio a gambe divaricate, mettendosi piede contro piede." +
                " Chi ha la palla deve cercare di farla passare tra le gambe di un altro giocatore, colpendola con le mani unite." +
                " Se un giocatore non riesce a parare il tiro e subisce un gol, deve portare una mano dietro la schiena; al gol successivo dovrà girarsi di spalle e potrà parare con due mani;" +
                " se ne subisce un altro potrà usare solo una mano; se incassa il quarto gol sarà eliminato." +
                "\nQuando rimarranno gli ultimi due giocatori si dovranno battere dei 'rigori' secondo modalità e distanze concordate dai giocatori."+
                "\nVince l'ultimo giocatore che rimane in gioco.",
                creator, date, password, isPublic, 2, 20, points);
    }
}
