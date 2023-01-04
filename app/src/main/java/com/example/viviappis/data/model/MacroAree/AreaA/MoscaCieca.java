package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class MoscaCieca  extends MacroA {

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
    public MoscaCieca(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "Mosca Cieca: per questa attività è necessaria una benda." +
                "\nTra i partecipanti viene scelta una 'Mosca'. Il giocatore mosca viene bendato e fatto girare su sè stesso diverse volte" +
                " per disorientarlo e al via deve riuscire a toccare gli altri, che possono muoversi liberamente intorno senza allontanarsi troppo dal posto originario." +
                " Appena la mosca tocca un giocatore, questo prende il suo posto." +
                "\n\nAlternativa divertente: quando la mosca tocca un giocatore deve anche indovinare chi è quel giocatore. Se sbaglia il gioco ricomincia e il giocatore mosca bendato rimane sempre lo stesso" +
                "\n\nTips: un gioco molto simile a mosca cieca è 'Marco Polo'. Le regole sono le stesse tuttavia quì, se il giocatore bendato si trova" +
                " in difficoltà, può chiamare: 'Marco' e tutti gli altri giocatori devono rispondergli: 'Polo'. Questa variante può essere applicata anche a Mosca Cieca per aiutare la mosca bendata che non riesce a toccare nessuno.",
                creator, date, password, isPublic, 5, 15, points);
    }
}
