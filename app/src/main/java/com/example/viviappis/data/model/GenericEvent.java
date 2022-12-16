package com.example.viviappis.data.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Questa classe serve a gestire GenericEvent
 * @author jacopo
 * @version 1.0
 */
public class GenericEvent extends Evento
{
    int minPart;
    int maxPart;

    /**
     * Genera un oggetto di tipo GenericEvent
     * @param name nome dell'evento
     * @param description descrizione dell'evento
     * @param creator creatore dell'evento
     * @param date data dell'evento
     * @param password password dell'evento
     * @param isPublic rappresenta se l'evento è publico
     * @param minPart numero minimo di partecipanti
     * @param maxPart numero massimo di partecipanti
     */
    public GenericEvent(String name, String description, String creator, String date, String password, boolean isPublic, int minPart, int maxPart)
    {
        super(name, description, creator, date, password, isPublic);
        this.minPart=minPart;
        this.maxPart=maxPart;
    }
    /**
     * Genera un oggetto di tipo GenericEvent
     * @param a oggetto di tipo GenericEvent per creare un nuovo oggetto
     */
    public GenericEvent(GenericEvent a) {this(a.getName(), a.getDescription(), a.getCreator(), a.getDate(), a.getPassword(),a.isPublic(), a.minPart, a.maxPart);}

    /**
     * Genera un oggetto di tipo GenericEvent
     * @param e oggetto che rappresenta i dati della super classe Evento
     * @param minPart numero minimo di partecipanti
     * @param maxPart numero massimo di partecipanti
     */
    public GenericEvent(Evento e, int minPart, int maxPart)
    {
        super(e);
        this.minPart=minPart;
        this.maxPart=maxPart;
    }


    /**
     * ritorna il valore minimo dei partecipanti
     * @return ritorna il valore minimo dei partecipanti
     */
    public int getMinPart() {return minPart;}
    /**
     * ritorna il valore massimo dei partecipanti
     * @return ritorna il valore massimo dei partecipanti
     */
    public int getMaxPart() {return maxPart;}


    /**
     * Permette di creare una mappa a partire dal genericEvent
     * @return la mappa che rappresenta il genericEvent
     */
    @Override
    public Map<String, Object> toMap()
    {
        Map<String, Object> event = super.toMap();
        event.put("minPart", this.minPart);
        event.put("maxPart", this.maxPart);

        return event;
    }
}
