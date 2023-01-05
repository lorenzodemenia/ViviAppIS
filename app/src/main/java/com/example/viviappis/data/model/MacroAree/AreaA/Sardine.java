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
     * @param points      points gained for the victory
     */
    public Sardine(String name, String desc, String creator, String date, String password, boolean isPublic, int points) {
        super(name, desc, creator, date, password, isPublic, 4, 15, points);
    }
}
