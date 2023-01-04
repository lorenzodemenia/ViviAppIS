package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class UnoDueTreStella extends MacroA {
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
    public UnoDueTreStella(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "1,2,3...STELLA!\nPer questo gioco molto famoso (il cui nome cambia in base alla regione in cui si gioca) non servono strumenti particolari, solo.. cercate di stare fermi!" +
                "\nPREPARATIVI: Deve essere scelto un 'capogioco' tra i giocatori. Il capogioco dovrà appoggiare la testa ad un albero od un muro coprendosi gli occhi." +
                " Gli altri partecipanti dovranno posizionarsi tutti dietro una linea orizzontale a 25-35 metri di distanza, alle spalle del capogioco." +
                "\nREGOLE: Il capogioco (con gli occhi chiusi e girato verso il muro) pronuncerà ad alta voce: 'Uno, Due, Tre... Stella!'. Non appena avrà detto 'stella' potrà girarsi ed osservare attentamente gli altri giocatori." +
                " Se il capogioco, mentre si sta girando o mentre è girato a guardarli, vede qualche giocatore muoversi allora quei giocatori vengono eliminati." +
                "\n\nOBBIETTIVO: Vince il giocatore (o i giocatori) che per primi riescono ad arrivare a toccare il muro e dire: 'STELLONE!', prima di essere eliminati.",
                creator, date, password, isPublic, 3, 20, points);
    }
}
