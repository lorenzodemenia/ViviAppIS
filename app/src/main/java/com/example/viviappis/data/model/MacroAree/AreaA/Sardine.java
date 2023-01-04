package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class Sardine extends MacroA {
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
    public Sardine(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "'Sardine' è un nascondino al contrario.\nSolo un giocatore si nasconde e tutti gli altri, dopo aver contato fino a 60, gli danno la caccia individualmente." +
                " Quando un cacciatore trova il nascondiglio si unisce alle prede fino a quando non stanno tutti nascosti e stretti come sardine! Il gioco continua fino a quando l'ultimo cacciatore trova le sardine." +
                " In questo gioco vincono tutti tranne l'ultimo cacciatore. Egli diventerà colui che si nasconderà per primo nel prossimo turno di gioco.",
                creator, date, password, isPublic, 4, 15, points);
    }
}
