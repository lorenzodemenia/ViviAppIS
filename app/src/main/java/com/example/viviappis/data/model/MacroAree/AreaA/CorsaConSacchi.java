package com.example.viviappis.data.model.MacroAree.AreaA;

import com.example.viviappis.data.model.Utente;

public class CorsaConSacchi extends MacroA {
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
    public CorsaConSacchi(String name, String creator, String date, String password, boolean isPublic, int minPart, int maxPart, int points) {
        super(name, "Per giocare alla corsa coi sacchi occorre un sacco per ogni partecipante della gara." +
                "\nTutti i concorrenti si dispongono sulla linea di partenza, con le gambe infilate nel sacco che terranno alzato fino alla vita con le mani." +
                "\nAl 'VIA!' dovranno correre o saltare come meglio possono fino a raggiungere per primi il traguardo finale!" +
                "\n\nAlternativa divertente: se il luogo dove si svolge l'attività lo permette si può tracciare un percorso. Vince chi riesce a percorrere per primo due volte il tracciato!",
                creator, date, password, isPublic, 3, 20, points);
    }
}
