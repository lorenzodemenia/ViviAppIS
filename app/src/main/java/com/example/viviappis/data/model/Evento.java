package com.example.viviappis.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Evento
{
    private String name;
    private String description;
    private final Utente creator;
    private String date;
    private final String password;
    private Collection<Utente> partecipants;
    private boolean isPublic;

    /**
     * Constructor of object Evento.
     * @param name nome dell'evento
     * @param description descrizione dell'evento
     * @param creator utente creatore dell'evento
     * @param date data e ora dell'evento
     * @param password password di accesso all'evento
     * @param isPublic definisce se l'evento è pubblico oppure privato
     */
    public Evento (String name, String description, Utente creator, String date, String password, boolean isPublic){
        this.name = name;
        this.creator = creator;
        this.description = description;
        this.password = password;
        this.date = date;
        this.partecipants = new ArrayList<>();
        this.isPublic = isPublic;
    }

    /**
     * Return the name of the event.
     * @return name of the event
     */
    public String getName() {
        return name;
    }

    /**
     * Return the password oth the event.
     * @return password of the event
     */
    public String getPassword() {
        return password;
    }

    /**
     * Return the time of the event.
     * @return time of the event
     */
    public String getDate() {
        return date;
    }

    /**
     * Return the users that partecipate to the event.
     * @return partecipants in the event
     */
    public Collection<Utente> getPartecipants() {
        return partecipants;
    }

    /**
     * Return the description of the event
     * @return description of the event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the users the created the event.
     * @return creator of the event
     */
    public Utente getCreator() {
        return creator;
    }

    /**
     * set the date of the instance to the value of the param date.
     * @param date time to set as time of the event
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * set the description of the instance to the value of the param description.
     * @param description to set as description of the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * set the name of the instance to the value of the param name.
     * @param name to set as name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set the isPublic of the instance to the value of the param isPublic.
     * @param isPublic value to set defining if the event is public or not
     */
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * add the user u to the partecipants list.
     * @param u user to add to partecipants list
     */
    public void addPartecipants(Utente u) {
        this.partecipants.add(u);
    }

    /**
     * Return if the event is public or not.
     * @return if the event is public or not
     */
    public boolean isPublic() {
        return isPublic;
    }

    /**
     * Create a Map of an event using Strings as keys and the fields of the event as values.
     * We use it in order to communicate the values of the event to the database.
     * @param e the event to push in the database
     * @return return a map having fields' name as keys and event fields' values as value
     */
    public static Map<String, Object> eventMap(Evento e){
        Map<String, Object> event = new HashMap<>();
        event.put("name", e.getName());
        event.put("description", e.getDescription());
        event.put("date", e.getDate());
        event.put("creator", e.getCreator());
        event.put("password", e.getPassword());
        event.put("partecipants", e.getPartecipants());
        event.put("public", e.isPublic());
        return event;
    }
}
