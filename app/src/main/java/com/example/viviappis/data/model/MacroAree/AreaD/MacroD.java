package com.example.viviappis.data.model.MacroAree.AreaD;

import com.example.viviappis.data.model.GenericEvent;
import com.example.viviappis.data.model.Utente;

public class MacroD extends GenericEvent {
    public MacroD(String name, String description, Utente creator, String date, String password, boolean isPublic, int minPart, int maxPart) {
        super(name, description, creator, date, password, isPublic, minPart, maxPart);
    }
}
