package com.example.viviappis.data.model.MacroAree.AreaA;


import com.example.viviappis.data.model.GenericEvent;
import com.example.viviappis.data.model.Utente;

public class MacroA extends GenericEvent
{

    /*
     **************elim
     * ********** inserire tutte le classi del tipo palla prigioniera ... dentro array.xml
     * **********
     */

    public MacroA(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart) {
        super(name, description, creator, date, password, isPublic, minPart, maxPart);
    }
}
