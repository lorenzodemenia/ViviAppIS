package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class Nascondino extends MacroA {
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
    public Nascondino(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "Nascondino è forse il più famoso gioco di movimento di gruppo da svolgere all'aperto. " +
                "Il gioco deve essere svolto in uno spazio ampio e aperto che offra la possibilità di nascondersi ovvero presenti alberi, cespugli, strutture dietro a cui rifugiarsi." +
                "\nViene scelto chi sarà il primo giocatore a 'stare sotto'. Il giocatore che sta sotto dovrà tenere la testa contro un muro od un albero che chiameremo 'Tana'. " +
                "Inizia il gioco: chi 'sta sotto' inizia a contare fino a 60 con gli occhi chiusi, nel frattempo tutti gli altri giocatori si nascondono. Terminata la conta, il giocatore che sta sotto urla: 'Via', si gira e va a cercare gli altri. " +
                "Quando trova un giocatore corre alla tana e urla il nome del giocatore, quel giocatore viene eliminato. Il primo giocatore eliminato sarà colui che farà la conta al prossimo turno." +
                "\nSe un giocatore raggiunge la tana prima di essere trovato ed dichiara 'tana' allora è salvo. L'ultimo giocatore rimasto in gioco può dichiarare 'tana libera tutti' liberando così tutti i giocatori precedentemente scoperti ed eliminati." +
                "\n\nAlternativa divertente: nel momento in cui chi sta sotto trova un giocatore nascosto deve cercare di toccarlo. Se riesce a toccarlo prima che quel giocatore faccia tana e si salvi allora, il giocatore toccato, non verrà eliminato ma dovrà invece cercare anche lui gli altri giocatori ed aiutare chi 'sta sotto'.",
                creator, date, password, isPublic, 3, 20, points);
    }
}
